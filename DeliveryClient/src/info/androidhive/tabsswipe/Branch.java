package info.androidhive.tabsswipe;

import java.util.HashMap;

public class Branch {
	private Integer id;
	private String name;
	private String description;
	private Area area;
	private String address;
	private String estimation_time;
	private int is_available;
	private Shop shop;
	private String longitude;
	private String latitude;
	private int open_hour;
	private int close_hour;
	private HashMap<Integer, String> froms, tos;
	private HashMap<Integer, Boolean> openDays;
	private OpenHours openHours;
	
	public Branch(int id,String name, Area area, String address)
	{
		this.id = id;
		this.name = name;
		this.setArea(area);
		this.address = address;
	}
	public Branch(int id, String name, String description, Area area,
			String address,  Shop shop, 
			String estimation_time) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.setArea(area);
		this.address = address;
		this.setShop(shop);
		this.estimation_time = estimation_time;
	} 

	public ValidationError validate() {
		boolean valid = false;
		int msg = 0;
		if (this.name.isEmpty() || this.name.length() < 3) {
			msg = R.string.invalid_name;
		} else if (this.address.isEmpty() || this.address.length() < 3) {
			msg = R.string.invalid_add;
		} else if (this.area.getId() == 0) {
			msg = R.string.invalid_area;
		} else if (this.estimation_time.isEmpty()
				|| this.estimation_time.length() < 2) {
			msg = R.string.invalid_time;
		} else {
			Double from, to;
			boolean error = true;
			for (int i = 0; i < 7; i++) {
				if (openDays.get(i) )
				{
					
					if(froms==null || tos==null || froms.get(i)==null || tos.get(i)==null)
					{
						msg = R.string.open_missing;
						error = false;
						break;
					}
					
					if (froms.get(i) != null
							&& tos.get(i) != null) {
						from = Double.parseDouble(froms.get(i));
						to = Double.parseDouble(tos.get(i));
						if (from >= to || from<0 || to <0) {
							msg = R.string.from_bigger;
							error = false;
							break;
						}
					}
			
				}
				
			}
			valid = error;
		}
		return new ValidationError(valid, msg);
	}

	public Branch(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getIs_available() {
		return is_available;
	}

	public void setIs_available(int is_available) {
		this.is_available = is_available;
	}

	public int getClose_hour() {
		return close_hour;
	}

	public void setClose_hour(int close_hour) {
		this.close_hour = close_hour;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getOpen_hour() {
		return open_hour;
	}

	public void setOpen_hour(int open_hour) {
		this.open_hour = open_hour;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getEstimation_time() {
		return estimation_time;
	}

	public void setEstimation_time(String estimation_time) {
		this.estimation_time = estimation_time;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public String toString() {
		if(this.area!=null)
		return this.name + "\n" + this.area.toString() + "," + this.address;
		else
			return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		Branch c = (Branch) obj;
		if (this.id == c.getId())
			return true;
		return false;
	}

	public HashMap<Integer, String> getFroms() {
		return froms;
	}

	public void setFroms(HashMap<Integer, String> froms) {
		this.froms = froms;
	}

	public HashMap<Integer, String> getTos() {
		return tos;
	}

	public HashMap<Integer, Boolean> getOpenDays() {
		return openDays;
	}

	public void setTos(HashMap<Integer, String> tos) {
		this.tos = tos;
	}

	public void setTosFroms(HashMap<Integer, String> froms,
			HashMap<Integer, String> tos, HashMap<Integer, Boolean> openDays) {
		this.froms = froms;
		this.tos = tos;
		this.openDays = openDays;
	}

	public OpenHours getOpenHours() {
		return openHours;
	}

	public void setOpenHours(OpenHours openHours) {
		this.openHours = openHours;
	}
}
