package com.pismo.creditcard.service;


import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.repository.OperationTypeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperationTypeServiceTest {

    @Mock
    private OperationTypeRepo operationTypeRepo;

    @InjectMocks
    private OperationTypeService operationTypeService;

    private OperationType sampleOperationType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleOperationType = new OperationType();
        sampleOperationType.setOperationTypeId(1L);
        sampleOperationType.setDescription("Test Operation");
    }

    @Test
    void testListOperations_Success() {
        List<OperationType> operationTypes = Arrays.asList(sampleOperationType);
        when(operationTypeRepo.getAllOperations()).thenReturn(operationTypes);

        List<OperationType> result = operationTypeService.listOperations();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(operationTypeRepo, times(1)).getAllOperations();
    }

    @Test
    void testListOperations_Empty() {
        when(operationTypeRepo.getAllOperations()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> operationTypeService.listOperations());
        verify(operationTypeRepo, times(1)).getAllOperations();
    }

    @Test
    void testGetOperationType_Success() {
        when(operationTypeRepo.getSpecifiedResource(1L)).thenReturn(sampleOperationType);

        OperationType result = operationTypeService.getOperationType(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOperationTypeId());
        verify(operationTypeRepo, times(1)).getSpecifiedResource(1L);
    }

    @Test
    void testGetOperationType_NotFound() {
        when(operationTypeRepo.getSpecifiedResource(1L)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> operationTypeService.getOperationType(1L));
        verify(operationTypeRepo, times(1)).getSpecifiedResource(1L);
    }
}