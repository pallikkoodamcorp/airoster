package com.ai.rostering.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ai.rostering.model.Recommendation;
import com.ai.rostering.repository.RecommendationRepository;

@Controller
public class ClassUIController {

	@Autowired
	RecommendationRepository recommendationRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(@RequestParam("pid") String pid,
			@RequestParam("errorCode") String errorCode, Model model) {
		
		List<Recommendation> recommendation = recommendationRepository.findRecommendationByPidErrorCode(pid, errorCode);
		model.addAttribute("recommendations", recommendation);
		return "welcome";
	}
	
}
