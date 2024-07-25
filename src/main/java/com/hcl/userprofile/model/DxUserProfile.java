package com.hcl.userprofile.model;

public class DxUserProfile {
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public DxUserProfile(String userId, String firstName, String lastName, String mail, String uid, String mobile) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mail = mail;
		this.uid = uid;
		this.mobile = mobile;
	}
	
	private String userId = "";	
	private String firstName = "";
	private String lastName = "";
	private String mail = "";
	private String uid = "";
	private String mobile= ""; 
	
	

}
