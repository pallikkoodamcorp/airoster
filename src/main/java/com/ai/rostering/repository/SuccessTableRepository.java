package com.ai.rostering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.SuccessTable;

@Repository
public interface SuccessTableRepository extends JpaRepository<SuccessTable, Long> {

}
