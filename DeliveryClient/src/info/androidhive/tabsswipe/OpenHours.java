package info.androidhive.tabsswipe;

import android.annotation.SuppressLint;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenHours {
	public HashMap<Integer, String> froms, tos;
	public HashMap<Integer, Boolean> openDays;
	public HashMap<Integer, String> days;

	@SuppressLint("UseSparseArrays")
	public OpenHours(Branch b) {
		
		this.froms = b.getFroms();
		this.tos = b.getTos();
		this.openDays = b.getOpenDays();
		this.days = new HashMap<Integer, String>();
		days.put(0, "mon");
		days.put(1, "tue");
		days.put(2, "wed");
		days.put(3, "thu");
		days.put(4, "fri");
		days.put(5, "sat");
		days.put(6, "sun");
	}
	public OpenHours(HashMap<Integer, String> froms, HashMap<Integer, String> tos,HashMap<Integer, Boolean> openDays)
	{
		this.froms=froms;
		this.tos=tos;
		this.openDays=openDays;							
	}
	

	public JSONArray getOpenHours() {
		JSONArray jsonArray = new JSONArray();
		try {

			for (int i = 0; i < 7; i++) {
				JSONObject day = new JSONObject();
				if (openDays.get(i) && froms.get(i)!=null && tos.get(i)!=null) {					
					day.put("from", froms.get(i));
					day.put("to", tos.get(i));
				} else {
					day.put("from", "null");
					day.put("to", "null");
				}
				day.put("day", days.get(i));
				jsonArray.put(day);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}
}
