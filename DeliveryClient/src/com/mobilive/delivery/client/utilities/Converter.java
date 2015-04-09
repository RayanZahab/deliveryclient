package com.mobilive.delivery.client.utilities;

public class Converter {

	public static int toInt(String word) {
		try {
			return Integer.parseInt(word);
		} catch (Exception nfe) {
			return 0;

		}

	}

	public static boolean toBoolean(String word) {
		try {
			return Boolean.parseBoolean(word);
		} catch (Exception nfe) {
			return false;

		}
	}

	public static Double toDouble(String word) {
		try {
			return Double.parseDouble(word);
		} catch (Exception nfe) {
			return 0.0;

		}
	}

}
