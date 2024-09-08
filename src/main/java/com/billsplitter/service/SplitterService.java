package com.billsplitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billsplitter.entity.Splitter;
import com.billsplitter.repository.SplitterRepository;

@Service
public class SplitterService {
	
	@Autowired
	SplitterRepository srepo;

	public List<Splitter> findMyToPayList(Long umob) {
		return srepo.findAllByMemberMobile(umob);
	}

	public void settleMyBill(int sid) {
		srepo.updateAmountStatus(sid);
	}

}
