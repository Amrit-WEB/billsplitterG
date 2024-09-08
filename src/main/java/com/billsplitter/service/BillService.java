package com.billsplitter.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billsplitter.entity.Bill;
import com.billsplitter.repository.BillRepository;

@Service
public class BillService {
	@Autowired
	BillRepository brepo;

	public void saveBill(Bill billobj) {
		brepo.save(billobj);
	}

	@Transactional
	public Long removeBill(int billid, long usermobile) {
		Long delcount = brepo.deleteByBillIdAndBillCreator(billid,usermobile);
		return delcount;
	}

	public List<Bill> getAllMyBill(Long umob) {
		return brepo.findAllByBillCreator(umob);
	}

	public List<Bill> getAllBillInWhichIPayer(Long umob) {	
		return brepo.findAllByBillPayer(umob);
	}

	public List<Bill> getAllBill() {
		return brepo.findAll();
	}


}
