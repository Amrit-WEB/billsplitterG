package com.billsplitter.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Bill {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int billId;
	private String billName;
	private Date billDate;
	private Long billCreator;
	private double billAmount;
	private Long billPayer;
	private String billPayerName;
	
	@OneToMany(cascade=CascadeType.ALL ,orphanRemoval=true)  
	@JoinColumn(name="billNumber")
	private List<Splitter> billMember; 
	
	public Bill() {
		super();
	}
	
	public Bill(int billId, String billName, Date billDate, Long billCreator,String billPayerName, double billAmount, Long billPayer,
			boolean billSettle,List<Splitter> billMember) {
		super();
		this.billId = billId;
		this.billName = billName;
		this.billDate = billDate;
		this.billCreator = billCreator;
		this.billAmount = billAmount;
		this.billPayer = billPayer;
		this.billPayerName = billPayerName;
		this.billMember = billMember;
	}

	

	public List<Splitter> getBillMember() {
		return billMember;
	}

	public void setBillMember(List<Splitter> billMember) {
		this.billMember = billMember;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public String getBillName() {
		return billName;
	}

	public void setBillName(String billName) {
		this.billName = billName;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Long getBillCreator() {
		return billCreator;
	}

	public void setBillCreator(Long billCreator) {
		this.billCreator = billCreator;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(double billAmount) {
		this.billAmount = billAmount;
	}

	public Long getBillPayer() {
		return billPayer;
	}

	public void setBillPayer(Long billPayer) {
		this.billPayer = billPayer;
	}


	public String getBillPayerName() {
		return billPayerName;
	}

	public void setBillPayerName(String billPayerName) {
		this.billPayerName = billPayerName;
	}

}
