package com.ai.rostering.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ai.rostering.model.Classroom;


public interface GenerateRecommendationService {

    public String generateRecommendations(String distpid);

	public Classroom applyRecommendations(@Valid Classroom classroom, String sugg);
}
