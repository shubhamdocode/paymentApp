package org.example.repository;

import org.example.entity.OperationsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationsTypeRepository extends JpaRepository<OperationsTypes,Integer> {
}
