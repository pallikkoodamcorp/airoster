package com.ai.rostering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.SuccessTable;

@Repository
public interface SuccessTableRepository extends JpaRepository<SuccessTable, Long> {
	
	@Query("select et from SuccessTable et where et.pid = ?#{[0]}")
	public List<SuccessTable> findSuccessTableByPid(String pid);

}
