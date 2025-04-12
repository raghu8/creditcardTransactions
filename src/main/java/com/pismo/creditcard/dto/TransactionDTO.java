package com.pismo.creditcard.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long transactionId;
    private Long accountId;
    private Long operationTypeId;
    private BigDecimal amount;
    private LocalDateTime eventDate;
}
