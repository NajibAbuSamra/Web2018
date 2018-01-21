package org.bookstop.model;

public class User {
	private String username;
	private String email;
	private String address;
	private String phone;
	private String password;
	private String nickname;
	private String description;
	private String picture;
	private int type;
	
	public User(String username, String email, String address, String phone, String password, String nickname, String description,
			String picture, int type) {
		super();
		this.username = username;
		this.email = email;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.nickname = nickname;
		this.description = description;
		this.picture = picture;
		this.type=type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public int getType() {
		return type;
	}
	
}
