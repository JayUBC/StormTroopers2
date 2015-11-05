package com.tutorial.gwt.client;

public class UserInfo {

	private String uid;
	
	private String lastName;
	
	private Integer age;
	
	private String gender;

	public UserInfo() {
	}

	public UserInfo(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
