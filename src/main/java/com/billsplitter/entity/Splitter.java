package com.billsplitter.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Splitter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	//private int billNumber; //ye mapping ke throug banega
	private long memberMobile;
	private String memberName;
	private double splitAmount;
	private boolean amountStatus = false;
	

	public Splitter() {
		super();
	}

	public Splitter(int id,long memberMobile,String memberName, double splitAmount,boolean amountStatus) {
		super();
		this.id = id;
		this.memberMobile = memberMobile;
		this.memberName = memberName;
		this.splitAmount = splitAmount;
		this.amountStatus = amountStatus;
	}

	public boolean isAmountStatus() {
		return amountStatus;
	}

	public void setAmountStatus(boolean amountStatus) {
		this.amountStatus = amountStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getMemberMobile() {
		return memberMobile;
	}

	public void setMemberMobile(long memberMobile) {
		this.memberMobile = memberMobile;
	}
	
	
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public double getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(double splitAmount) {
		this.splitAmount = splitAmount;
	}
	
}
