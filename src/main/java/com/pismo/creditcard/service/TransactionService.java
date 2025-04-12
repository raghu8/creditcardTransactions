package com.pismo.creditcard.service;

import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.model.Transaction;
import com.pismo.creditcard.repository.AccountRepo;
import com.pismo.creditcard.repository.OperationTypeRepo;
import com.pismo.creditcard.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        Transaction transaction = Transaction.builder()
                .account(account)
                .operationType(operationType)
                .amount(transactionDto.getAmount())
                .eventDate(transactionDto.getEventDate())
                .build();

        LocalDateTime eventDate = LocalDateTime.now();
        transactionRepo.insertTransaction(transaction.getAccount().getAccountId(),
                transaction.getOperationType().getOperationTypeId(),
                transaction.getAmount(),
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
