package com.ai.rostering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

	@Query("select r from Recommendation r where r.pid = ?#{[0]} and r.errorCode = ?#{[1]}")
	List<Recommendation> findRecommendationByPidErrorCode(String pid, String errorCode);
}
