package com.pismo.creditcard.service;

import com.pismo.creditcard.exception.DuplicateObjectException;
import com.pismo.creditcard.exception.InvalidObjectException;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.repository.AccountRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Account getAccount(Long id){
        log.info("retrieving account: ",id);
        Account account = accountRepo.getAccountById(id);
        if (account==null) {
            log.info("account could not be retrived.");
            throw new ResourceNotFoundException("Account with id: " +id+" not found");
        }
        return account;
    }

    public void createAccount(Account account){
        /*
         * I'm making the following assumptions
         * 1. document number should contain only digits
         * 2. document number is unique
         * 3. document number should not be null
         */
        log.info("creating account");
        verifyDocumentNumber(account);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);
         accountRepo.insertAccount(account.getDocumentNumber(),account.getDescription(),now);
    }

    public void verifyDocumentNumber(Account account){
        /*
         * Can use this method to implement other checks on the document number such as length of the document
         * or that it starts and stops with a certain range of digits. The exception below can be used for
         * giving the user why the document verification has failed.
         */
        log.info("Null and empty check of document number.");
        // Ensure the document number is not null or empty
        if (account.getDocumentNumber() == null || account.getDocumentNumber().trim().isEmpty()) {
            throw new InvalidObjectException("Document number is a required value");
        }

        log.info("Ensuring document number is not alpha-numeric");
        if (!account.getDocumentNumber().matches("\\d+")) {
            throw new InvalidObjectException("Document number should only contain numbers");
        }

        //Checking to see if the document is unique
        log.info("Checking uniqueness of the document");
        Account duplicateDocumentNumber = accountRepo.getAccountByDocumentNumber(account.getDocumentNumber());
        if(duplicateDocumentNumber!=null){
            throw new DuplicateObjectException("Document number already exists");
        }
    }

}
