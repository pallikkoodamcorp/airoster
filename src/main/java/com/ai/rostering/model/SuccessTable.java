package com.ai.rostering.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SuccessTable {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	private String pid;
	
	private Long batchId;
	
	private String identifier;
	
	public SuccessTable() {
		super();
		// TODO Auto-generated constructor stub
	}


	public SuccessTable(Long id, String pid, Long batchId, String identifier) {
		super();
		this.id = id;
		this.pid = pid;
		this.batchId = batchId;
		this.identifier = identifier;
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

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
}
