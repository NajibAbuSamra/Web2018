package org.bookstop.model;

public class UserLogin {
	String uName;
	String uPass;
	
	public UserLogin(String uName, String uPass) {
		super();
		this.uName = uName;
		this.uPass = uPass;
	}
	
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPass() {
		return uPass;
	}
	public void setuPass(String uPass) {
		this.uPass = uPass;
	}
	
	

}
