package com.mobilife.delivery.client.utilities;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneInfoManager {
	public  static String getPhoneImei(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getDeviceId();
		return number;
	}
	
	public static boolean containZeroAsPrefix(String phoneNumber){
		
		if(phoneNumber!=null && phoneNumber.length()>0){
			char c = phoneNumber.charAt(0);
			if(c == '0')
				return true;
		}
		return false;
	}

}
