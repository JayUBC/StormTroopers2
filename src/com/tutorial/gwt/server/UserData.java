package com.tutorial.gwt.server;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserData {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private String uid;
	@Persistent
	private String lastName;
	@Persistent
	private Integer age;
	@Persistent
	private String gender;
	@Persistent
	private Date createDate;

	public UserData() {
		this.createDate = new Date();
	}

	public UserData(User user, String uid, String lastName, Integer age, String gender) {
		this();
		this.user = user;
		this.uid = uid;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
	}

	public Long getId() {
		return this.id;
	}

	public User getUser() {
		return this.user;
	}

	public String getUid() {
		return this.uid;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public Integer getAge() {
		return this.age;
	}
	
	public String getGender() {
		return this.gender;
	}

	public Date getCreateDate() {
		return this.createDate;
		
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
}