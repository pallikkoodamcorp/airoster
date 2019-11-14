package com.ai.rostering.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ai.rostering.model.Classroom;
import com.ai.rostering.model.ErrorTable;
import com.ai.rostering.model.Recommendation;
import com.ai.rostering.model.SuccessTable;
import com.ai.rostering.model.UserForm;
import com.ai.rostering.repository.ClassRepository;
import com.ai.rostering.repository.ErrorTableRepository;
import com.ai.rostering.repository.RecommendationRepository;
import com.ai.rostering.repository.SuccessTableRepository;
import com.ai.rostering.service.GenerateRecommendationService;
import com.google.gson.Gson;

@Controller
public class ClassUIController {

	@Autowired
	RecommendationRepository recommendationRepository;

	@Autowired
	private ClassRepository classRepository;

	@Autowired
	private ErrorTableRepository errorTableRepository;

	@Autowired
	private SuccessTableRepository successTableRepository;

	@Autowired
	private GenerateRecommendationService recommendationService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model) {
		model.addAttribute("userForm", new UserForm());
		return "welcome";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
		String content = null;
		try {
			content = new String(file.getBytes());
			System.out.println(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Classroom classroom = null;
		try {
			Gson g = new Gson();
			classroom = g.fromJson(content, Classroom.class);
			System.out.println(classroom.getClassName());
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
			recommendationService.generateRecommendations(classroom.getPid());
			// return classroom;
			modelMap.addAttribute("userForm", new UserForm());
			modelMap.addAttribute("result", "Import Success");
		} catch (Exception e) {

			System.out.println("unique constriant error:" + e.getMessage());
			String errorCode = "1920";

			List<Recommendation> recList = recommendationRepository.findRecommendationByPidErrorCode(classroom.getPid(),
					errorCode);
			if (!recList.isEmpty()) {
				Recommendation rec = recList.get(0);
				String acc = rec.getAcceptance();
				String sugg = "";
				if (acc!= null && acc.equals("S")) {
					sugg = rec.getSystemSuggested();
					recommendationService.applyRecommendations(classroom, sugg);
					modelMap.addAttribute("userForm", new UserForm());
					modelMap.addAttribute("result", "Import Success");
				} else if (acc!=null && acc.equals("U")) {
					sugg = rec.getUserSuggested();
					recommendationService.applyRecommendations(classroom, sugg);
					modelMap.addAttribute("userForm", new UserForm());
					modelMap.addAttribute("result", "Import Success");
				}else if (acc!=null && acc.equals("I")) {
					/*ErrorTable errorTable = new ErrorTable();
					errorTable.setPid(classroom.getPid());
					errorTable.setIdentifier(classroom.getClassId());
					errorTable.setErrorCode(errorCode);
					errorTable.setIdentifierValue(classroom.getClassName());
					errorTableRepository.save(errorTable);*/
					
					modelMap.addAttribute("result", "Import Failed, Error Code:" + errorCode);
					/*List<Recommendation> recommendations = recommendationRepository
							.findRecommendationByPidErrorCode(classroom.getPid(), errorCode);
					modelMap.addAttribute("recommendations", recommendations);*/
					modelMap.addAttribute("userForm", new UserForm());
				}else{
					modelMap.addAttribute("result", "Import Failed, Error Code:" + errorCode);
					List<Recommendation> recommendations = recommendationRepository
							.findRecommendationByPidErrorCode(classroom.getPid(), errorCode);
					modelMap.addAttribute("recommendations", recommendations);
					modelMap.addAttribute("userForm", new UserForm());
				}
			} else {
				ErrorTable errorTable = new ErrorTable();
				errorTable.setPid(classroom.getPid());
				errorTable.setIdentifier(classroom.getClassId());
				errorTable.setErrorCode(errorCode);
				errorTable.setIdentifierValue(classroom.getClassName());
				errorTableRepository.save(errorTable);

				modelMap.addAttribute("result", "Import Failed, Error Code:" + errorCode);
				List<Recommendation> recommendations = recommendationRepository
						.findRecommendationByPidErrorCode(classroom.getPid(), errorCode);
				modelMap.addAttribute("recommendations", recommendations);
				modelMap.addAttribute("userForm", new UserForm());
			}
		}
		return "welcome";
	}

	@RequestMapping(value = "/submitUserRecommendation", method = RequestMethod.POST)
	public String submitUserRecommendation(@ModelAttribute("userForm") UserForm userForm, Model model) {
		System.out.println(userForm.getAcceptance());
		System.out.println(userForm.getCustomerRecommended());
		System.out.println(userForm.getRecommendationId());
		/*
		 * Recommendation recommendation = new Recommendation();
		 * recommendation.setId(Long.parseLong(userForm.getRecommendationId()));
		 * recommendation.setAcceptance(userForm.getAcceptance());
		 */
		recommendationRepository.updateRecommentation(userForm.getAcceptance(), userForm.getCustomerRecommended(),
				Long.parseLong(userForm.getRecommendationId()));
		return "welcome";
	}

	/*
	 * public String uploadFile1(@RequestParam("file") MultipartFile file,
	 * ModelMap modelMap) {     modelMap.addAttribute("file", file);     return
	 * "fileUploadView"; }
	 */

	/*
	 * @RequestMapping(value = "/importFile", method = RequestMethod.GET) public
	 * String getImportFile() {
	 * 
	 * System.out.println("Pid value:"+pid);
	 * System.out.println("Error Code value:"+errorCode);
	 * 
	 * List<Recommendation> recommendation =
	 * recommendationRepository.findRecommendationByPidErrorCode(pid,
	 * errorCode);
	 * 
	 * System.out.println("Recommendation Value:"+recommendation);
	 * if(recommendation!=null){
	 * System.out.println("Leng:"+recommendation.size()); }
	 * 
	 * model.addAttribute("recommendations", recommendation);
	 * 
	 * return "welcome"; }
	 */

}
