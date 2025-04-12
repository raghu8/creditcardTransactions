package com.pismo.creditcard.service;

import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pismo.creditcard.repository.OperationTypeRepo;

import java.util.List;

@Service
public class OperationTypeService {
    @Autowired
    private OperationTypeRepo operationTypeRepo;

    public List<OperationType> listOperations() {
        List<OperationType> operations = operationTypeRepo.getAllOperations();
        if (operations.isEmpty()) {
            throw new ResourceNotFoundException("No operation types found");
        }
        return operations;
    }

    public OperationType getOperationType(Long id){
        OperationType operationType = operationTypeRepo.getSpecifiedResource(id);
        if (operationType==null) {
            throw new ResourceNotFoundException("No operation types found");
        }
        return operationType;
    }
}
