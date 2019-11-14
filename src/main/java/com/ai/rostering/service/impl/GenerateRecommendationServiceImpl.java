package com.ai.rostering.service.impl;

import com.ai.rostering.controller.ClassController;
import com.ai.rostering.model.Classroom;
import com.ai.rostering.model.ErrorTable;
import com.ai.rostering.model.SuccessTable;
import com.ai.rostering.repository.ClassRepository;
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

    @Override
    public String generateRecommendations(String distpid) {
        List<ErrorTable> errorTables =  new ArrayList<ErrorTable>();
        List<SuccessTable> successTables = new ArrayList<SuccessTable>();
        getMatchedSucessData(errorTables,successTables);
        return "";
    }

    private void getMatchedSucessData(List<ErrorTable> errorTables,List<SuccessTable> successTables){
        errorTables.forEach(errorTable -> {
            if(errorTable.getErrorCode().equalsIgnoreCase("1920")){
                successTables.forEach(successTable -> {
                    if(errorTable.getIdentifier().equalsIgnoreCase(successTable.getIdentifier())){
                        Classroom classData = classRepository.findClassroomByClassId(successTable.getIdentifier());
                        String day1 = errorTable.getIdentifierValue();
                        String day2 = successTable.getIdentifierValue();
                        String grade = classData.getGrade();
                        //String period = classData.get;
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
                    }
                });
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
}
