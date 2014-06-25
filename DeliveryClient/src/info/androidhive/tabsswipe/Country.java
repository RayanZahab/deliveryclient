package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.util.Log;

public class Country {
	private Integer id;
	private String name;
	private ArrayList<City> cities;
	public Country(Integer id, String name) {
		this.setId(id);
		this.setName(name);
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

	public ArrayList<City> getCities(ArrayList<City> cities) {
		ArrayList<City> currentCities = new ArrayList<City>();
		for (int i = 0; i < cities.size(); i++) {
			if (cities.get(i).getCountry_id() == id) {
				currentCities.add(cities.get(i));
			}
		}
		return currentCities;
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
}
