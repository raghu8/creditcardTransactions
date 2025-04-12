package com.pismo.creditcard.repository;

import com.pismo.creditcard.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface OperationTypeRepo extends JpaRepository<OperationType,Long> {
    @Query(value = "select * from operation_type",nativeQuery = true)
    List<OperationType> getAllOperations();

    @Query(value = "select * from operation_type where operation_type_id = :id limit 1",nativeQuery = true)
    OperationType getSpecifiedResource(@RequestParam("id")Long id);
}
