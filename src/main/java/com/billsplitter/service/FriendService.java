package com.billsplitter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billsplitter.entity.Friend;
import com.billsplitter.repository.FriendRepository;

@Service
public class FriendService {
	@Autowired
	FriendRepository frepo;

	public void saveRequest(Friend obj) {
		frepo.save(obj);
	}
	
	public List<Friend> getAllFriendRequest(Long uMob) {
		return frepo.findByToMobileAndAcceptStatus(uMob,false);
	}

	public void AcceptRequest(Long fromMob, Long toMob) {
		frepo.updateAcceptStatus(fromMob,toMob);
	}

	public void deleteRequest(Long fromMob, Long toMob) {
		frepo.deleteFriends(fromMob,toMob,false);
	}

	public List<Friend> getAllMyFriends(Long uMob) {
		return frepo.findByFromMobileAndAcceptStatus(uMob,true);
	}

	public void removeMyFriend(Long umob, Long fmob, boolean b) {
		frepo.deleteFriends(umob,fmob,b);
	}

	public List<Friend> findThisNumber(Long toMob,Long fromMob) {
		return frepo.findByToMobileAndFromMobile(toMob,fromMob);
	}
}
