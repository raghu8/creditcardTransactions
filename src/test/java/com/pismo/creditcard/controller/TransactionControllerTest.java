package com.pismo.creditcard.controller;

import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.service.OperationTypeService;
import com.pismo.creditcard.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @Mock
    private OperationTypeService operationTypeService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private TransactionDTO sampleTransaction;
    private OperationType sampleOperationType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleTransaction = new TransactionDTO();
        sampleTransaction.setTransactionId(1L);
        sampleTransaction.setAccountId(1L);
        sampleTransaction.setOperationTypeId(1L);
        sampleTransaction.setAmount(BigDecimal.valueOf(100.00));
        sampleTransaction.setEventDate(LocalDateTime.now());

        sampleOperationType = new OperationType();
        sampleOperationType.setOperationTypeId(1L);
        sampleOperationType.setDescription("Test Operation");
    }

    @Test
    void testGetAllOperationTypes() {
        List<OperationType> operationTypes = Arrays.asList(sampleOperationType);
        when(operationTypeService.listOperations()).thenReturn(operationTypes);

        ResponseEntity<List<OperationType>> response = transactionController.getAllOperationTypes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(operationTypes, response.getBody());
        verify(operationTypeService, times(1)).listOperations();
    }

    @Test
    void testGetOperationType() {
        when(operationTypeService.getOperationType(1L)).thenReturn(sampleOperationType);

        ResponseEntity<OperationType> response = transactionController.getOperationType(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleOperationType, response.getBody());
        verify(operationTypeService, times(1)).getOperationType(1L);
    }

    @Test
    void testCreateTransaction() {
        doNothing().when(transactionService).createTransaction(sampleTransaction);

        ResponseEntity<String> response = transactionController.createTransaction(sampleTransaction);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Transaction created successfully", response.getBody());
        verify(transactionService, times(1)).createTransaction(sampleTransaction);
    }

    @Test
    void testGetTransaction() {
        when(transactionService.getTransaction(1L)).thenReturn(sampleTransaction);

        ResponseEntity<TransactionDTO> response = transactionController.getTransaction(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleTransaction, response.getBody());
        verify(transactionService, times(1)).getTransaction(1L);
    }
}