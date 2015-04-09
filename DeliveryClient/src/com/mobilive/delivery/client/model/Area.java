package com.mobilive.delivery.client.model;

public class Area {
	private Integer id;
	private String name;
	private Integer city_id;
	private int country_id;
	private String json;
	
	public Area(int id) {
		this.id = id;
	}
	public Area(int id,String name) {
		this.id = id;
		this.name = name;
	}

	public Area(int id, int city_id, String name) {
		this.id = id;
		this.name = name;
		this.city_id = city_id;
	}

	public Area(int id, int city_id,int country_id, String name) {
		this.id = id;
		this.name = name;
		this.city_id = city_id;
		this.country_id = country_id;
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

	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public String toString() {
		return name;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	@Override
	public boolean equals(Object obj) {
		Area area = (Area) obj;
		if(this.id == area.getId())
			return true;
		return false;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}
