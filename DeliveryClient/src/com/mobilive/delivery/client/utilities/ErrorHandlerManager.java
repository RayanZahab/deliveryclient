package com.mobilive.delivery.client.utilities;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.mobilive.delivery.client.R;


public class ErrorHandlerManager {

	private static ErrorHandlerManager instance;
	private Map<String,Integer> errosMap = new HashMap<String, Integer>();
	
	private ErrorHandlerManager(){
		fillHashMap();
	}
	
	private void fillHashMap() {
		errosMap.put("blocked customer", R.string.blockedcustomer);
		errosMap.put("customer not found", R.string.customerNotFound);
		errosMap.put("area not found", R.string.areaNotFound);
		errosMap.put("branch not found", R.string.branchNotFound);
		errosMap.put("category not found", R.string.categoryNotFound);
		errosMap.put("business not found", R.string.businessNotFound);
		errosMap.put("city not found", R.string.cityNotFound);
		errosMap.put("country not found", R.string.countryNotFound);
		errosMap.put("Mobile has already been taken", R.string.mobileTaken);
		errosMap.put("Code is not correct", R.string.codeNotCorrect);
		errosMap.put("User not found", R.string.userNotFound);
		errosMap.put("Max reset password is reached", R.string.maxRestPass);
		errosMap.put("Device is not registered", R.string.deviceNotRegistered);
		errosMap.put("customer is not activated or locked", R.string.customerNotActivated);
		errosMap.put("item not found", R.string.itemNotFound);
		errosMap.put("order not found", R.string.orderNotFound);
		errosMap.put("shop not found", R.string.shopNotFound);
		errosMap.put("unit not found", R.string.unitNotFound);
		errosMap.put("user not found", R.string.userNotFound);
	}

	public static ErrorHandlerManager getInstance() {
	      if(instance == null) {
	         instance = new ErrorHandlerManager();
	      }
	      return instance;
	 }
	
	public String getErrorString(Activity activity,String lang,String error){
		Resources resources = getResources(activity,lang);
		String str = resources.getString(R.string.defaultError);
		error= parseError(error);
		if(errosMap.get(error)!=null)
			str = resources.getString(errosMap.get(error));
		return str;	
	}
	
	private Resources getResources(Activity activity,String lang){
		Configuration conf = activity.getResources().getConfiguration();
		conf.locale = new Locale(lang);
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Resources resources = new Resources(activity.getAssets(), metrics, conf);
		return resources;
	}
	
	private String parseError(String error){
		String res = "";
		int index1= error.indexOf("{\"error\":\"");
		int index2= error.indexOf("\"}");
		res = error.substring(index1+"{\"error\":\"".length(),index2);
		return res;
	}
	
}


