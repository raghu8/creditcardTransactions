package com.pismo.creditcard.repository;

import com.pismo.creditcard.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {

    @Query(value="Select * from Account where account_id = :id",nativeQuery = true)
    Account getAccountById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Account (document_number, description, created_date) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertAccount(String documentNumber, String description, LocalDateTime createdDate);

    @Query(value = "select * from Account where document_number = :document limit 1",nativeQuery = true)
    Account getAccountByDocumentNumber(@Param("document")String document);
}
