package com.mobilife.delivery.client.model;

public class Unit {
	private int id;
	private String name;

	public Unit(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return this.name;
	}
	@Override
	public boolean equals(Object obj) {
		Unit u =(Unit) obj;
		if (this.id ==u.id)
			return true;
		return false;
	}

}
