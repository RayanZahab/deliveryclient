package com.mobilive.delivery.client.model;

public class CodeVerificationRequest {

	private String mobileNumber;
	private String code;
	
	
	public CodeVerificationRequest() {
		super();
	}
	
	public CodeVerificationRequest(String mobileNumber, String code) {
		super();
		this.mobileNumber = mobileNumber;
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	
}
