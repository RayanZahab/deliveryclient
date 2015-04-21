package com.mobilive.delivery.client.model;

public class Business {
	private int id;
	private String name;
	private String photo;

	public Business(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public void setPhotoWithParse(String photo) {
		if(photo!=null)
			this.photo = parsePhoto(photo);
	}
	
	private String parsePhoto(String error){
		String res = "";
		int index1= error.indexOf("{\"url\":\"");
		int index2= error.indexOf("\"}");
		if(index1!=-1 && index2!=-1){
			res = error.substring(index1+"{\"url\":\"".length(),index2);
			res = res.replaceAll("\\\\", "");
		}else
			res = null;
		return res;
	}
}
