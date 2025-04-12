package com.pismo.creditcard.service;

import static org.junit.jupiter.api.Assertions.*;
import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.Account;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.model.Transaction;
import com.pismo.creditcard.repository.AccountRepo;
import com.pismo.creditcard.repository.OperationTypeRepo;
import com.pismo.creditcard.repository.TransactionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private OperationTypeRepo operationTypeRepo;

    @InjectMocks
    private TransactionService transactionService;

    private TransactionDTO sampleTransaction;
    private Account sampleAccount;
    private OperationType sampleOperationType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleAccount = Account.builder()
                .accountId(1L)
                .documentNumber("123456789")
                .description("Test Account")
                .build();

        sampleOperationType = new OperationType();
        sampleOperationType.setOperationTypeId(1L);
        sampleOperationType.setDescription("Test Operation");

        sampleTransaction = new TransactionDTO();
        sampleTransaction.setTransactionId(1L);
        sampleTransaction.setAccountId(1L);
        sampleTransaction.setOperationTypeId(1L);
        sampleTransaction.setAmount(BigDecimal.valueOf(100.00));
        sampleTransaction.setEventDate(LocalDateTime.now());
    }

    @Test
    void testCreateTransaction_Success() {
        when(accountRepo.getAccountById(1L)).thenReturn(sampleAccount);
        when(operationTypeRepo.getSpecifiedResource(1L)).thenReturn(sampleOperationType);
        doNothing().when(transactionRepo).insertTransaction(anyLong(), anyLong(), any(BigDecimal.class), any(LocalDateTime.class));

        transactionService.createTransaction(sampleTransaction);

        verify(accountRepo, times(1)).getAccountById(1L);
        verify(operationTypeRepo, times(1)).getSpecifiedResource(1L);
        verify(transactionRepo, times(1)).insertTransaction(anyLong(), anyLong(), any(BigDecimal.class), any(LocalDateTime.class));
    }

    @Test
    void testCreateTransaction_AccountNotFound() {
        when(accountRepo.getAccountById(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> transactionService.createTransaction(sampleTransaction));
        verify(accountRepo, times(1)).getAccountById(1L);
    }

    @Test
    void testGetTransaction_Success() {
        Transaction transaction = Transaction.builder()
                .transactionId(1L)
                .account(sampleAccount)
                .operationType(sampleOperationType)
                .amount(BigDecimal.valueOf(100.00))
                .eventDate(LocalDateTime.now())
                .build();

        when(transactionRepo.getTransaction(1L)).thenReturn(transaction);

        TransactionDTO result = transactionService.getTransaction(1L);

        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        verify(transactionRepo, times(1)).getTransaction(1L);
    }

    @Test
    void testGetTransaction_NotFound() {
        when(transactionRepo.getTransaction(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> transactionService.getTransaction(1L));
        verify(transactionRepo, times(1)).getTransaction(1L);
    }
}