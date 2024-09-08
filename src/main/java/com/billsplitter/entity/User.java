package com.billsplitter.entity;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class User {

	@Id
	private Long mobile;
	private String name;
	private String password;
	
	//Constructors
	public User() {
		super();
	}

	public User(String name, String password, Long mobile) {
		super();
		this.name = name;
		this.password = password;
		this.mobile = mobile;
	}
	
	//Getter Setter Methods
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

}
