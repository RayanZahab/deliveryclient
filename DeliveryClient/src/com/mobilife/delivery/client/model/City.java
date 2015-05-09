package com.mobilife.delivery.client.model;

import java.util.ArrayList;

import android.util.Log;

public class City {
	private int id;
	private int country_id;
	private String name;
	private ArrayList<Area> areas = new ArrayList<Area>();
	private String json;
	
	public City(int id,String name) {
		this.setId(id);
		this.setName(name);
	}
	public City(int id,int country,String name) {
		this.setId(id);
		this.setCountry_id(country_id);
		this.setName(name);
	}
	public City(int id)
	{
		this.setId(id);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
	@Override
	public boolean equals(Object obj) {
		City c = (City) obj;
		if(this.id == c.getId())
			return true;
		return false;
	}
	public boolean equalsString(Object obj) {
		City c = (City) obj;
		if(this.name.equals( c.getName()))
			return true;
		return false;
	}
	public ArrayList<Area> getAreas() {
		return areas;
	}
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
}
