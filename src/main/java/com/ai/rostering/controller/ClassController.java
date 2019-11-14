
package com.ai.rostering.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ai.rostering.model.Classroom;
import com.ai.rostering.model.ErrorTable;
import com.ai.rostering.model.SuccessTable;
import com.ai.rostering.repository.ClassRepository;
import com.ai.rostering.repository.ErrorTableRepository;
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
  
  /**
   * Get all users list.
   *
   * @return the list
   */
  @GetMapping(path="/classes", produces=MediaType.APPLICATION_JSON_VALUE)
  public List<Classroom> getAllClasses() {
    return classRepository.findAll();
  }
  /**
   * Create user user.
   *
   * @param user the user
   * @return the user
   */
  @PostMapping(path = "/classes", consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
  public Classroom createClass(@Valid @RequestBody Classroom classroom) {
	  try {
		  classRepository.save(classroom);	  
		  List<ErrorTable> list = errorTableRepository.findErrorTableByPid(classroom.getPid());
		  if(list!=null && !list.isEmpty()){
			  SuccessTable st = new SuccessTable();
			 st.setBatchId(classroom.getId());
			 st.setPid(classroom.getPid());
			 st.setIdentifier(classroom.getClassName());
			 successTableRepository.save(st);
		  }
      } catch (Exception e) {
          System.out.println("unique constriant error:" + e.getMessage());
          String errorCode = "1920";
          ErrorTable errorTable = new ErrorTable();
          errorTable.setPid(classroom.getPid());
          errorTable.setBatchId(classroom.getId());
          errorTable.setErrorCode(errorCode);
          errorTable.setIdentifier(classroom.getClassName());
          errorTableRepository.save(errorTable);
      }
	return classroom;
  }
}
