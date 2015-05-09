package com.mobilife.delivery.client.model;


public class Customer {
	private int id;
	private String name;
	private String username;
	private String password;
	private String phone;
	private String mobile;
	private int is_allowed;
	private int default_address_id;
	private Photo photo;
	private String token;
	private Gender gender;

	public Customer(int id, String name, String username, String password,
			String phone, String mobile, int is_allowed, int default_address_id) {
		this.setId(id);
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setPhone(phone);
		this.setMobile(mobile);
		this.setIs_allowed(is_allowed);
		this.setDefault_address_id(is_allowed);
		this.default_address_id = default_address_id;
	}

	public Customer(int id, String name, String Phone, String mobile,
			Photo photo) {
		this.setId(id);
		this.setName(name);
		this.setPhone(phone);
		this.setMobile(mobile);
		this.setPhoto(photo);
	}

	public Customer(int id, String name,String token) {
		this.setId(id);
		this.setName(name);
		this.setToken(token);
	}

	public int getIs_allowed() {
		return is_allowed;
	}

	public void setIs_allowed(int is_allowed) {
		this.is_allowed = is_allowed;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public int getDefault_address_id() {
		return default_address_id;
	}

	public void setDefault_address_id(int default_address_id) {
		this.default_address_id = default_address_id;
	}

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}
	public String toString()
	{
		return this.name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
