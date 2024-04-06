package org.example.repository;

import org.example.entity.Accounts;
import org.example.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsRepository extends JpaRepository<org.example.entity.Transactions,Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM transactiondb.Transactions  where Account_ID=:accId and Balance < :bal")
   List <Transactions> findAllTransactionByAccId(@Param("accId") int accId,@Param("bal") int balance);
}
