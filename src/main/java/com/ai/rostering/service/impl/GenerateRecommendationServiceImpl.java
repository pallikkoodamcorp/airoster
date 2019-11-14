package com.ai.rostering.service.impl;

import com.ai.rostering.controller.ClassController;
import com.ai.rostering.model.Classroom;
import com.ai.rostering.model.ErrorTable;
import com.ai.rostering.model.Recommendation;
import com.ai.rostering.model.SuccessTable;
import com.ai.rostering.repository.ClassRepository;
import com.ai.rostering.repository.RecommendationRepository;
import com.ai.rostering.service.GenerateRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenerateRecommendationServiceImpl implements GenerateRecommendationService {

    @Autowired
    ClassController classController;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    RecommendationRepository recommendationRepository;

    @Override
    public String generateRecommendations(String distpid) {
        List<ErrorTable> errorTables =  classController.findErrorTableByPid(distpid);
        List<SuccessTable> successTables = classController.findSuccessByPid(distpid);
        if(errorTables !=null && successTables != null) {
            getMatchedSuccessData(errorTables, successTables);
            return "generated";
        }
        return "no recommendations";
    }

    private void getMatchedSuccessData(List<ErrorTable> errorTables,List<SuccessTable> successTables){
        errorTables.forEach(errorTable -> {
            List<Recommendation> recommendationList= recommendationRepository.findRecommendationByPidErrorCode(errorTable.getPid(),errorTable.getErrorCode());
            if(recommendationList ==null ){
                if(errorTable.getErrorCode().equalsIgnoreCase("1920")){
                    successTables.forEach(successTable -> {
                        if(errorTable.getIdentifier().equalsIgnoreCase(successTable.getIdentifier())){
                            Classroom classData = classRepository.findClassroomByClassId(successTable.getIdentifier());
                            String day1 = errorTable.getIdentifierValue();
                            String day2 = successTable.getIdentifierValue();
                            String grade = classData.getGrade();
                            String teacher = classData.getTeacherUser();
                            String additional = "";
                            StringBuffer pattern=new StringBuffer();
                            if (day2.contains(day1)) {
                                additional = findSubString(day2,day1,pattern,0);
                            }
                            if (additional != "") {
                                if (additional.indexOf(teacher) != -1) {
                                    String addon = findSubString(additional,teacher,pattern,1);
                                    generatePattern(pattern,"teachername",addon);
                                } else if (additional.indexOf(grade) != -1) {
                                    String addon = findSubString(additional,grade,pattern,1);
                                    generatePattern(pattern,"grade",addon);
                                }
                            }
                            Recommendation recommendation = new Recommendation();
                            recommendation.setErrorCode("1920");
                            recommendation.setPid(errorTable.getPid());
                            recommendation.setSystemSuggested(pattern.toString());
                            recommendationRepository.save(recommendation);

                        } else{
                     }
                    });
                }
            }
        });
    }

    private  String findSubString(String main,String sub,StringBuffer pattern,int iter){
        int index = main.indexOf(sub);
        String subString ="";
        if (index == 0) {
            System.out.println("found");
            subString = main.substring(index+ sub.length(), main.length());
            if(iter ==0)
                pattern.append( "suffix");
        } else {
            System.out.println(main.substring(0, index));
            subString = main.substring(0, index);
            if(iter ==0)
                pattern.append( "prefix");
        }
        return subString;
    }

    public static void generatePattern(StringBuffer pattern, String type, String val){
        if(pattern.toString().equalsIgnoreCase("prefix")) {
            pattern.append(type);
            pattern.append(val);
        } else {
            pattern.append(val);
            pattern.append(type);
        }
    }
    
    
    public Classroom applyRecommendations(Classroom classroom, String sugg) {
    	if(sugg.equals("suffix_teachername")) {
    		classroom.setClassName(classroom.getClassName()+"_"+classroom.getTeacherUser());
    	}
    	classRepository.save(classroom);
    	return classroom;
    }
}
