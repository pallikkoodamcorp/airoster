
package com.ai.rostering.controller;

import java.util.List;

import javax.validation.Valid;

import com.ai.rostering.service.GenerateRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ai.rostering.model.Classroom;
import com.ai.rostering.model.ErrorTable;
import com.ai.rostering.model.Recommendation;
import com.ai.rostering.model.SuccessTable;
import com.ai.rostering.repository.ClassRepository;
import com.ai.rostering.repository.ErrorTableRepository;
import com.ai.rostering.repository.RecommendationRepository;
import com.ai.rostering.repository.SuccessTableRepository;

/**
 * The type User controller.
 *
 * @author karthickumarvp
 */
@RestController
@RequestMapping("/api/v1")
public class ClassController {

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ErrorTableRepository errorTableRepository;

	@Autowired
	private SuccessTableRepository successTableRepository;
	
	@Autowired
	private RecommendationRepository recommendationRepository;

	@Autowired
	private GenerateRecommendationService recommendationService;

	/**
	 * Get all users list.
	 *
	 * @return the list
	 */
	@GetMapping(path = "/classes", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Classroom> getAllClasses() {
		return classRepository.findAll();
	}

	/**
	 * Create class.
	 *
	 */
	@PostMapping(path = "/classes", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public Classroom createClass(@Valid @RequestBody Classroom classroom) {
		boolean isSusccessfull = true;
		try {
			classRepository.save(classroom);
			List<ErrorTable> list = errorTableRepository.findErrorTableByPid(classroom.getPid(),
					classroom.getClassId());
			if (list != null && !list.isEmpty()) {
				SuccessTable st = new SuccessTable();
				st.setIdentifier(classroom.getClassId());
				st.setPid(classroom.getPid());
				st.setIdentifierValue(classroom.getClassName());
				successTableRepository.save(st);
			}
			return classroom;
		} catch (Exception e) {
			System.out.println("unique constriant error:" + e.getMessage());
			String errorCode = "1920";
			ErrorTable errorTable = new ErrorTable();
			errorTable.setPid(classroom.getPid());
			errorTable.setIdentifier(classroom.getClassId());
			errorTable.setErrorCode(errorCode);
			errorTable.setIdentifierValue(classroom.getClassName());
			errorTableRepository.save(errorTable);
			recommendationService.generateRecommendations(classroom.getPid());
			throw e;
		}
	}

	@GetMapping("/getRecommendation")
	public List<Recommendation> getRecommendationForErrorCode(@RequestParam("pid") String pid,
			@RequestParam("errorCode") String errorCode) {
		return recommendationRepository.findRecommendationByPidErrorCode(pid, errorCode);
	}

	@PostMapping("/saveRecommendation")
	public void saveRecommendation(@RequestBody Recommendation recommendation) {
		recommendationRepository.save(recommendation);
	}
}
