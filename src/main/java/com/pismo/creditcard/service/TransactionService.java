package com.pismo.creditcard.service;

import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.enums.OperationTypeEnum;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.model.Transaction;
import com.pismo.creditcard.repository.AccountRepo;
import com.pismo.creditcard.repository.OperationTypeRepo;
import com.pismo.creditcard.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    
    @Autowired
    private AccountRepo accountRepo;
    
    @Autowired
    private OperationTypeRepo operationTypeRepo;

    public void createTransaction(TransactionDTO transactionDto) {
        //validating transactions
        Account account = accountRepo.getAccountById(transactionDto.getAccountId());
        OperationType operationType = operationTypeRepo.getSpecifiedResource(transactionDto.getOperationTypeId());
        validateAccountAndOperationType(account,operationType);

        /*
         * Transactions of purchase and withdrawal are always negative amounts,
         *  whereas payments are positive amounts.
         */
        BigDecimal amount = transactionDto.getAmount();
        OperationTypeEnum operationTypeEnum = OperationTypeEnum.fromId(operationType.getOperationTypeId().intValue());

        if (operationTypeEnum == OperationTypeEnum.NORMAL_PURCHASE ||
                operationTypeEnum == OperationTypeEnum.PURCHASE_WITH_INSTALLMENTS ||
                operationTypeEnum == OperationTypeEnum.WITHDRAWAL) {
            amount = amount.negate();
        }

        Transaction transaction = Transaction.builder()
                .account(account)
                .operationType(operationType)
                .amount(transactionDto.getAmount())
                .eventDate(transactionDto.getEventDate())
                .build();

        LocalDateTime eventDate = LocalDateTime.now();
        transactionRepo.insertTransaction(transaction.getAccount().getAccountId(),
                transaction.getOperationType().getOperationTypeId(),
                amount,
                eventDate);
    }


    private void validateAccountAndOperationType(Account account, OperationType operationType) {
        if(account==null){
            throw new ResourceNotFoundException("Account specified in transaction does not exist");
        }
        if(operationType==null){
            throw new ResourceNotFoundException("Operation specified is not supported");
        }
    }

    public TransactionDTO getTransaction(Long id) {
        Transaction transaction = transactionRepo.getTransaction(id);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction id: " + id + " not found");
        }

        // Map Transaction to TransactionResponseDTO
        TransactionDTO responseDTO = new TransactionDTO();
        responseDTO.setTransactionId(transaction.getTransactionId());
        responseDTO.setAccountId(transaction.getAccount().getAccountId());
        responseDTO.setOperationTypeId(transaction.getOperationType().getOperationTypeId());
        responseDTO.setAmount(transaction.getAmount());
        responseDTO.setEventDate(transaction.getEventDate());

        return responseDTO;
    }
}
