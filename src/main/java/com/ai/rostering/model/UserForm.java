package com.ai.rostering.model;


public class UserForm {

	public String getAcceptance() {
		return acceptance;
	}

	public String getCustomerRecommended() {
		return customerRecommended;
	}

	public void setCustomerRecommended(String customerRecommended) {
		this.customerRecommended = customerRecommended;
	}

	public void setAcceptance(String acceptance) {
		this.acceptance = acceptance;
	}

	private String acceptance;
	
	private String recommendationId;
	
	

	public String getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(String recommendationId) {
		this.recommendationId = recommendationId;
	}

	private String customerRecommended;
}
