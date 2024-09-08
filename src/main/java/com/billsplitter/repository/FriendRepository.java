package com.billsplitter.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.billsplitter.entity.Friend;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<Friend,Integer>{
	
	public List<Friend> findByToMobileAndAcceptStatus(Long tMob,boolean status);

	
	@Modifying
	@Query("UPDATE Friend f SET f.acceptStatus = TRUE WHERE f.fromMobile = :fromMob AND f.toMobile = :toMob")
	public void updateAcceptStatus(@Param(value = "fromMob") Long fromMob, @Param(value = "toMob") Long toMob);

	@Modifying
	@Query("DELETE FROM Friend f WHERE f.fromMobile = :fromMob AND f.toMobile = :toMob AND acceptStatus = :opr")
	public void deleteFriends(Long fromMob, Long toMob, boolean opr);


	public List<Friend> findByFromMobileAndAcceptStatus(Long fMob, boolean status);


	public List<Friend> findByToMobileAndFromMobile(Long toMob, Long fromMob);
	
}
