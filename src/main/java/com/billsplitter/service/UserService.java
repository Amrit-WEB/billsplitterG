package com.billsplitter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.billsplitter.entity.User;
import com.billsplitter.exception.CustomException;
import com.billsplitter.repository.UserRepository;

@Service
public class UserService{
	
	@Autowired
	private UserRepository urepo;

	public String saveTheUser(User obj) throws CustomException {
		User alreadyUser = urepo.findById(obj.getMobile()).orElse(null);
		if(alreadyUser == null) {
			urepo.save(obj);
			return "Account Created Successfully 👍";
		}else {
			throw new CustomException("User Already Exixts");
		}
	}

	public List<User> getAllMyFriends() {
		return urepo.findAll();
	}

	public Optional<User> getSearchFriend(long mobile) {
		return urepo.findById(mobile);
	}

	public String getUserName(Long umob) {
		Optional <User> uo = urepo.findById(umob);
		User u = uo.get();
		String name = u.getName();
		return name;
	}

	public List<User> getValidUser(Long mobile, String password) {
		return urepo.findByMobileAndPassword(mobile,password);
	}

	

	

}
