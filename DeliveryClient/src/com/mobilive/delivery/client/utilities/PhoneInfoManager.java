package com.mobilive.delivery.client.utilities;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneInfoManager {
	public  static String getPhoneImei(Context context){
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tm.getDeviceId();
		return number;
	}

}
