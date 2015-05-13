package com.mobilife.delivery.client.model;

import java.util.ArrayList;

import com.mobilife.delivery.client.utilities.Converter;

public class Address {
	private Integer id;
	private String country, countryName,cityName,areaName;
	private String city;
	private String area;
	private String building;
	private String floor;
	private String street;
	private String details;
	private Integer customer_id;
	private String longitude;
	private String latitude;
	private String update_at;
	private String created_at;
	private boolean isDefault;

	public Address() {
	}

	public Address(int id) {
		this.id = id;
	}

	public Address(int id, String country, String city, String area,
			String building, String floor, String street, String details,
			int customer_id, String longitude, String latitude,
			String created_at, String update_at, Boolean is_default) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.area = area;
		this.building = building;
		this.floor = floor;
		this.street = street;
		this.details = details;
		this.customer_id = customer_id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.setCreated_at(created_at);
		this.setUpdate_at(update_at);
		this.isDefault = is_default;

	}

	public Address(int id, String country, String city, String area,
			String building, String floor, String street, String details) {
		this.id = id;
		this.country = country;
		this.city = city;
		this.area = area;
		this.building = building;
		this.floor = floor;
		this.street = street;
		this.details = details;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setAddress_long(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String toString(ArrayList<Country> countries) {
		int countryIndex = countries.indexOf(new Country(Converter.toInt(this.getCountry())));
		if(countryIndex>-1)
		{
			Country country = countries.get(countryIndex);
			this.setCountryName(country.toString());
		
			int cityIndex = country.getCities().indexOf(new City(Converter.toInt(this.getCity())));
			if(countryIndex>-1)
			{
				City city = country.getCities().get(cityIndex);
				this.setCityName(city.toString());
				int areaIndex = city.getAreas().indexOf(new Area(Converter.toInt(this.getArea())));
				if(areaIndex>-1)
				{
					Area area = city.getAreas().get(areaIndex);
					this.setAreaName(area.toString());
				}
			}			
		}
		String addressString = "";
		String[] part1 = new String[]{this.getCountryName(),this.getCityName(),this.getAreaName()};
		String[] part2 = new String[]{this.street,this.building, this.floor,this.details};
		
		addressString = strJoin(part1,",") + " \n"+ strJoin(part2,",") ;
		
		return addressString;
	}
	
	public static String strJoin(String[] aArr, String sSep) {
	    StringBuilder sbStr = new StringBuilder();
	    for (int i = 0, il = aArr.length; i < il; i++) {
	        if(aArr[i].length()>0)
	        {
		        if (i > 0)
		            sbStr.append(sSep);
	        	sbStr.append(aArr[i]);
	        }
	    }
	    return sbStr.toString();
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String toString() {
		return this.building + " , " + this.floor + " , " + this.details;
	}

}
