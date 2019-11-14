package com.ai.rostering.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ai.rostering.model.Recommendation;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

	@Query("select r from Recommendation r where r.pid = ?#{[0]} and r.errorCode = ?#{[1]}")
	public List<Recommendation> findRecommendationByPidErrorCode(String pid, String errorCode);
	
	@Transactional
	@Modifying
	@Query("update Recommendation r set r.acceptance = ?#{[0]}, r.userSuggested = ?#{[1]} where r.id = ?#{[2]}")
	public void updateRecommentation(String acceptance, String userSuggested, Long id);
}
