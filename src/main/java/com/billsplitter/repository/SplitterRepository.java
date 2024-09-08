package com.billsplitter.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.billsplitter.entity.Splitter;

@Repository
@Transactional
public interface SplitterRepository extends JpaRepository<Splitter,Integer> {

	List<Splitter> findAllByMemberMobile(Long umob);

	@Modifying
	@Query("UPDATE Splitter s SET s.amountStatus = TRUE WHERE s.id = :sid")
	void updateAmountStatus(int sid);
	

}
