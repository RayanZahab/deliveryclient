package com.mobilive.delivery.client.model;

import java.util.ArrayList;

import android.util.Log;

public class Country {
	private Integer id;
	private String name;
	private String json;
    private String countrCode;
    private String isoCode;
    
	private ArrayList<City> cities = new ArrayList<City>();
	
	public Country(Integer id, String name) {
		this.setId(id);
		this.setName(name);
	}
	public Country(Integer id, String name,String countrCode, String isoCode) {
		this.setId(id);
		this.setName(name);
		this.countrCode = countrCode;
		this.isoCode = isoCode;
	}
	public Country(Integer id) {
		this.setId(id);
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

	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		Country c = (Country) obj;
		Log.d("ray","Cont: "+c.getId() +" == "+this.id);
		if(this.id == c.getId())
			return true;
		return false;
	}
	public ArrayList<City> getCities() {
		return cities;
	}
	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}
	public String getJson() {
		return json;
	}
	public void setJson(String json) {
		this.json = json;
	}
	public String getCountrCode() {
		return countrCode;
	}
	public void setCountrCode(String countrCode) {
		this.countrCode = countrCode;
	}
	public String getIsoCode() {
		return isoCode;
	}
	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}
}
