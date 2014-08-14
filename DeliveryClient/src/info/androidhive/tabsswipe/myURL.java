package info.androidhive.tabsswipe;

public class myURL {
	private String urlPrefix = "http://107.170.86.46/api/v1/";
	private String url = "", by, api;
	private int limit, value;
	private String svalue;

	public myURL(String api, String by, int value, int limit) {
		this.api = api;
		this.by = by;
		this.value = value;
		this.limit = limit;
	}

	public myURL(String api, String by, String svalue, int limit) {
		this.api = api;
		this.by = by;
		this.setSvalue(svalue); 
		this.limit = limit;
	}

	public String getURL() {

		String currentVal = "";
		if (svalue != null) {
			currentVal = svalue;
		} else {
			currentVal = "" + value;
		}

		if (by == null) {
			url += api;
		} else if (api == null) {
			url += url + by + "/" + currentVal;
		} else if (api != null) {
			url += url + by + "/" + currentVal + "/" + api;
		}

		if (limit > 0) {
			url += "?limit=" + limit;
		}
		return urlPrefix + url;
	}

	public String getSvalue() {
		return svalue;
	}

	public void setSvalue(String svalue) {
		this.svalue = svalue;
	}
}
