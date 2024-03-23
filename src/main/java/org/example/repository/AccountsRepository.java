package org.example.repository;

import org.example.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountsRepository extends JpaRepository<Accounts,Integer> {

    @Query(
    nativeQuery = true,
    value = "SELECT * FROM transactiondb.Accounts where Document_Number=:docId")
   Accounts findAccountByDocumentId(@Param("docId") int docId);
}
