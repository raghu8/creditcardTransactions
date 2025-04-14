package com.pismo.creditcard.controller;

import com.pismo.creditcard.dto.TransactionDTO;
import com.pismo.creditcard.model.OperationType;
import com.pismo.creditcard.service.OperationTypeService;
import com.pismo.creditcard.service.TransactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@Log4j2
public class TransactionController {
    @Autowired
    private OperationTypeService operationTypeService;

    @Autowired
    private TransactionService transactionService;
    @GetMapping("/operations")
    public ResponseEntity<List<OperationType>> getAllOperationTypes() {
        log.info("Listing all operation types");
        List<OperationType> operations = operationTypeService.listOperations();
        return ResponseEntity.ok(operations);
    }

    @GetMapping("/operations/type/{id}")
    public ResponseEntity<OperationType> getOperationType(@PathVariable Long id){
        log.info("Retrieving operationId: ",id);
        return ResponseEntity.ok(operationTypeService.getOperationType(id));
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTransaction(
            @RequestBody TransactionDTO transactionDTO) {
        log.info("Creating transaction");
        transactionService.createTransaction(transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created successfully");
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id){
        log.info("Retrieving transaction: ",id);
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }
}
