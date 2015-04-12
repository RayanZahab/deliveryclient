package com.mobilive.delivery.client.model;

public class ForgetPasswordRequest {

	private String mobileNumber;
	private String mobileImei;
	
	
	public ForgetPasswordRequest() {
		super();
	}
	
	public ForgetPasswordRequest(String mobileNumber, String mobileImei) {
		super();
		this.mobileNumber = mobileNumber;
		this.mobileImei = mobileImei;
	}
	
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileImei() {
		return mobileImei;
	}

	public void setMobileImei(String mobileImei) {
		this.mobileImei = mobileImei;
	}
	
	
}

