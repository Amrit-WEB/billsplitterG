package com.billsplitter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Friend {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private Long fromMobile;
	private String fromName;
	private Long toMobile;
	private String toName;
	private boolean acceptStatus=false;
	
	public Friend() {
		super();
	}


	public Friend(int id, Long fromMobile, String fromName, Long toMobile, String toName, boolean acceptStatus) {
		super();
		this.id = id;
		this.fromMobile = fromMobile;
		this.fromName = fromName;
		this.toMobile = toMobile;
		this.toName = toName;
		this.acceptStatus = acceptStatus;
	}

	
	public String getFromName() {
		return fromName;
	}


	public void setFromName(String fromName) {
		this.fromName = fromName;
	}


	public String getToName() {
		return toName;
	}


	public void setToName(String toName) {
		this.toName = toName;
	}


	public Long getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(Long fromMobile) {
		this.fromMobile = fromMobile;
	}

	public Long getToMobile() {
		return toMobile;
	}

	public void setToMobile(Long toMobile) {
		this.toMobile = toMobile;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(boolean acceptStatus) {
		this.acceptStatus = acceptStatus;
	}
	
	
}
