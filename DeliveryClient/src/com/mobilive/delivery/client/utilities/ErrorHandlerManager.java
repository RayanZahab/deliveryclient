package com.mobilive.delivery.client.utilities;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.mobilive.delivery.client.R;


public class ErrorHandlerManager {

	private static ErrorHandlerManager instance;
	private Map<String,Integer> errorsMap = new HashMap<String, Integer>();
	
	private ErrorHandlerManager(){
		fillHashMap();
	}
	
	private void fillHashMap() {
		errorsMap.put("blocked customer", R.string.blockedcustomer);
		errorsMap.put("customer not found", R.string.customerNotFound);
		errorsMap.put("area not found", R.string.areaNotFound);
		errorsMap.put("branch not found", R.string.branchNotFound);
		errorsMap.put("category not found", R.string.categoryNotFound);
		errorsMap.put("business not found", R.string.businessNotFound);
		errorsMap.put("city not found", R.string.cityNotFound);
		errorsMap.put("country not found", R.string.countryNotFound);
		errorsMap.put("Mobile has already been taken", R.string.mobileTaken);
		errorsMap.put("Code is not correct", R.string.codeNotCorrect);
		errorsMap.put("User not found", R.string.userNotFound);
		errorsMap.put("Max reset password is reached", R.string.maxRestPass);
		errorsMap.put("Device is not registered", R.string.deviceNotRegistered);
		errorsMap.put("customer is not activated or locked", R.string.customerNotActivated);
		errorsMap.put("item not found", R.string.itemNotFound);
		errorsMap.put("order not found", R.string.orderNotFound);
		errorsMap.put("shop not found", R.string.shopNotFound);
		errorsMap.put("unit not found", R.string.unitNotFound);
		errorsMap.put("user not found", R.string.userNotFound);
		errorsMap.put("please enter User Name and Password",R.string.enterUserAndPass);
		errorsMap.put("A new Password was sent by SMS.", R.string.checkYourMessages);
		errorsMap.put("Pass is empty or do not match", R.string.passEmptyOrDMatch);
		errorsMap.put("No Areas exist in this City", R.string.noArea);
		errorsMap.put("No Cities exist in this Country", R.string.noCity);
		errorsMap.put("Sorry the Shop is closed now..", R.string.shopIsClose);
		errorsMap.put("Phone Number can not start with zero..", R.string.startWithZeroErr);
	}

	public static ErrorHandlerManager getInstance() {
	      if(instance == null) {
	         instance = new ErrorHandlerManager();
	      }
	      return instance;
	 }
	
	public String getErrorString(Activity activity,String error){
		String str = activity.getString(R.string.defaultError);
		error= parseError(error);
		if(errorsMap.get(error)!=null)
			str = activity.getString(errorsMap.get(error));
		return str;	
	}
	
	private String parseError(String error){
		int index1= error.indexOf("{\"error\":\"");
		int index2= error.indexOf("\"}");
		if(index1!=-1 && index2!=-1)
			error = error.substring(index1+"{\"error\":\"".length(),index2);
		return error;
	}
	
}


