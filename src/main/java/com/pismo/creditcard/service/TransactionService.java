package com.pismo.creditcard.service;

import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.enums.OperationTypeEnum;
import com.pismo.creditcard.exception.InvalidObjectException;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.model.Transaction;
import com.pismo.creditcard.repository.AccountRepo;
import com.pismo.creditcard.repository.OperationTypeRepo;
import com.pismo.creditcard.repository.TransactionRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Log4j2
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepo;
    
    @Autowired
    private AccountRepo accountRepo;
    
    @Autowired
    private OperationTypeRepo operationTypeRepo;



    public void createTransaction(TransactionDTO transactionDto) {
        log.info("creating transaction");
        log.info("validating operationType: ",transactionDto.getOperationTypeId());
        OperationType verifiedOperationType = verifiedOperationType(transactionDto.getOperationTypeId());

        //validating account
        log.info("validating account: ",transactionDto.getAccountId());
        Account verifiedAccount = verifiedAccount(transactionDto.getAccountId());

        log.info("validating input amount: ",transactionDto.getAmount());
        if(transactionDto.getAmount()==null||transactionDto.getAmount().equals(0)){
            throw new InvalidObjectException("amount can't be zero or null");
        }
        /*
         * Transactions of purchase and withdrawal are always negative amounts,
         *  whereas payments are positive amounts.
         */
        BigDecimal amount = transactionDto.getAmount();
        OperationTypeEnum operationTypeEnum = OperationTypeEnum.fromId(verifiedOperationType.getOperationTypeId().intValue());

        log.info("Converting all non payment operations to negative values");
        if (operationTypeEnum == OperationTypeEnum.NORMAL_PURCHASE ||
                operationTypeEnum == OperationTypeEnum.PURCHASE_WITH_INSTALLMENTS ||
                operationTypeEnum == OperationTypeEnum.WITHDRAWAL) {
            amount = amount.negate();
        }

        Transaction transaction = Transaction.builder()
                .account(verifiedAccount)
                .operationType(verifiedOperationType)
                .amount(transactionDto.getAmount())
                .eventDate(transactionDto.getEventDate())
                .build();

        LocalDateTime eventDate = LocalDateTime.now();
        transactionRepo.insertTransaction(transaction.getAccount().getAccountId(),
                transaction.getOperationType().getOperationTypeId(),
                amount,
                eventDate);
    }

    private Account verifiedAccount(Long accountId) {
        log.info("retriving account specified by transaction: ",accountId);
        Account account = accountRepo.getAccountById(accountId);
        if(account==null){
            throw new ResourceNotFoundException("Account specified in transaction does not exist");
        }

        return account;
    }

    private OperationType verifiedOperationType(Long operationTypeId) {
        log.info("verifying operation type specified by transaction: ",operationTypeId);
        OperationType operationType = operationTypeRepo.getSpecifiedResource(operationTypeId);
        if(operationType==null){
            throw new ResourceNotFoundException("Operation specified is not supported");
        }
        return operationType;
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
