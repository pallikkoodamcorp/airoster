package com.ai.rostering.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder*/
@Entity
public class Recommendation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String pid;
	
	private String errorCode;

	private String systemSuggested;
	
	private String userSuggested;
	
	private String acceptance;
	
	
	public Recommendation() {
		super();
	}

	public Recommendation(String pid, String errorCode, String systemSuggested, String userSuggested,
			String acceptance) {
		super();
		this.pid = pid;
		this.errorCode = errorCode;
		this.systemSuggested = systemSuggested;
		this.userSuggested = userSuggested;
		this.acceptance = acceptance;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSystemSuggested() {
		return systemSuggested;
	}

	public void setSystemSuggested(String systemSuggested) {
		this.systemSuggested = systemSuggested;
	}

	public String getUserSuggested() {
		return userSuggested;
	}

	public void setUserSuggested(String userSuggested) {
		this.userSuggested = userSuggested;
	}

	public String getAcceptance() {
		return acceptance;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}
}
