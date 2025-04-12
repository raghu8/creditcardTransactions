package com.pismo.creditcard.repository;

import com.pismo.creditcard.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Transaction (account_id, operation_type_id, amount, event_date) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertTransaction(Long accountId, Long operationTypeId, BigDecimal amount, LocalDateTime eventDate);

    @Query(value = "Select * from Transaction where transaction_id = :id limit 1", nativeQuery = true)
    Transaction getTransaction(Long id);
}
