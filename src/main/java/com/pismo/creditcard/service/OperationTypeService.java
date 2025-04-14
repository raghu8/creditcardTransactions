package com.pismo.creditcard.service;

import com.pismo.creditcard.exception.ResourceNotFoundException;
import com.pismo.creditcard.model.OperationType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pismo.creditcard.repository.OperationTypeRepo;

import java.util.List;

@Service
@Log4j2
public class OperationTypeService {
    @Autowired
    private OperationTypeRepo operationTypeRepo;

    public List<OperationType> listOperations() {
        log.info("Listing all operations");
        List<OperationType> operations = operationTypeRepo.getAllOperations();
        if (operations.isEmpty()) {
            throw new ResourceNotFoundException("No operation types found");
        }
        return operations;
    }

    public OperationType getOperationType(Long id){
        log.info("Retriving operation type: ",id);
        OperationType operationType = operationTypeRepo.getSpecifiedResource(id);
        if (operationType==null) {
            throw new ResourceNotFoundException("No operation types found");
        }
        return operationType;
    }
}
