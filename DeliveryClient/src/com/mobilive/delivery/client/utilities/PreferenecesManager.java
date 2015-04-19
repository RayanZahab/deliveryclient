package com.mobilive.delivery.client.utilities;

import android.app.Activity;
import android.content.SharedPreferences;



public class PreferenecesManager {

	private static PreferenecesManager instance;
	
	private PreferenecesManager(){
	}
	
	public static PreferenecesManager getInstance() {
	      if(instance == null) {
	         instance = new PreferenecesManager();
	      }
	      return instance;
	 }
	
	 public String getCurrentLanguage(Activity activity){
		String currentLang = "en";
		SharedPreferences sharedPreferences = activity.getSharedPreferences("PREFS_NAME", 0);
		if(sharedPreferences.getString("lang", null)!=null)
			currentLang= sharedPreferences.getString("lang", null);

		return currentLang;
	 }
	
}

