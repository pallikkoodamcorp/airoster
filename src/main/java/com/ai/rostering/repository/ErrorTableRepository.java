package com.ai.rostering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.ErrorTable;

@Repository
public interface ErrorTableRepository extends JpaRepository<ErrorTable, Long>{
	
	@Query("select et from ErrorTable et where et.pid = ?#{[0]} and et.identifier = ?#{[1]}")
	public List<ErrorTable> findErrorTableByPid(String pid, String identifier);
}
