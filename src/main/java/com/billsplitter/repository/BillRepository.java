package com.billsplitter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billsplitter.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

	Long deleteByBillIdAndBillCreator(int billid, long usermobile);

	List<Bill> findAllByBillCreator(Long umob);

	List<Bill> findAllByBillPayer(Long umob);

}
