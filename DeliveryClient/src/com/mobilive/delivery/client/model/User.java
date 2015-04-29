package com.mobilive.delivery.client.model;


import java.io.Serializable;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.utilities.ValidationError;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 477550760291642297L;
	private int id;
	private String name;
	private String username;
	private String password;
	private String phone, token;
	private String imei;
	private Gender gender;
	private int is_fired;
	private Address address;
	private int branch_id;
	private boolean is_admin, is_preparer, is_delivery, login, is_customer;
	

	public User(int id, String name, String password, String phone,
			int is_fired, Address address, int branch_id, boolean is_admin,
			boolean is_preparer, boolean is_delivery) {

		this.setId(id);
		this.setName(name);
		this.setUsername(username);
		
		this.setPhone(phone);
		this.setIs_fired(is_fired);
		this.setAddress(address);
		this.setBranch_id(branch_id);
		this.setIs_admin(is_admin);
		this.setIs_preparer(is_preparer);
		this.setIs_delivery(is_delivery);
	}

	public User(String name, String phone, String pass, int branch_id,
			int is_fired) {
		this.setName(name);
		this.setPhone(phone);
		this.setIs_fired(is_fired);
		this.setBranch_id(branch_id);
	}

	public User(int id, String name, String token, int branch_id,
			boolean is_admin, boolean is_preparer, boolean is_delivery) {
		this.setName(name);
		this.setId(id);
		this.setBranch_id(branch_id);
		this.setIs_admin(is_admin);
		this.setIs_preparer(is_preparer);
		this.setIs_delivery(is_delivery);
		this.setToken(token);
	}

	public User(String phone, String password) {
		this.setPassword(password);
		this.setPhone(phone);
		this.setLogin(true);
	}

	public User(int id) {
		this.setId(id);
	}

	public User(int i, String name) {
		this.setId(id);
		this.setName(name);
	}

	public User() {
		is_customer = true;
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
			this.password=password;
	}
	public void setEncPassword(String password)
	{
		this.password = Hashing.sha256().hashString(password, Charsets.UTF_8)
				.toString();
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

	public int getIs_fired() {
		return is_fired;
	}

	public void setIs_fired(int is_fired) {
		this.is_fired = is_fired;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String toString() {
		return this.name;
	}

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public boolean isIs_delivery() {
		return is_delivery;
	}

	public void setIs_delivery(boolean is_delivery) {
		this.is_delivery = is_delivery;
	}

	public boolean isIs_admin() {
		return is_admin;
	}

	public void setIs_admin(boolean is_admin) {
		this.is_admin = is_admin;
	}

	public boolean isIs_preparer() {
		return is_preparer;
	}

	public void setIs_preparer(boolean is_preparer) {
		this.is_preparer = is_preparer;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ValidationError validate(boolean mine) {
		boolean valid = false;
		int msg = 0;
		if (this.name.isEmpty() || this.name.length() < 3) {
			msg = R.string.invalid_name;
		} else if (this.phone==null || this.phone.isEmpty() || this.phone.length() < 8) {
			msg = R.string.invalid_phone;
		}else
			valid = true;
		return new ValidationError(valid, msg);
	}

	@Override
	public boolean equals(Object obj) {
		User u = (User) obj;
		if (this.id == u.getId())
			return true;
		return false;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isIs_customer() {
		return is_customer;
	}

	public void setIs_customer(boolean is_customer) {
		this.is_customer = is_customer;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
