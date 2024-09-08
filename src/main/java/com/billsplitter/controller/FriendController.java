package com.billsplitter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.billsplitter.entity.Friend;
import com.billsplitter.entity.User;
import com.billsplitter.service.FriendService;
import com.billsplitter.service.UserService;

@Controller
public class FriendController {
	@Autowired
	private UserService uservice;
	
	@Autowired
	private FriendService fservice;
	
	
	@GetMapping("/friends")
	public String friends(Model res ,@Param("mob") String mob,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			
			res.addAttribute("username",username);
			
			//Friend Searching Logic
			if(mob != null) {
				long mobile = Long.parseLong(mob);
				if(mobile == umob) {
					res.addAttribute("message", "Why? you search yourself 🙄");
					res.addAttribute("referLocation", "/friends");
					return "message";
				}else {
					Optional<User> searchFriendResultOptional = uservice.getSearchFriend(mobile);
					if(searchFriendResultOptional.isPresent()) {
						User searchFriend = searchFriendResultOptional.get();
						res.addAttribute("searchResult",searchFriend);
					}else {
						res.addAttribute("searchResult",0);
					}
				}
			}else {
				res.addAttribute("searchResult", null);
			}
			
			//Getting Friend Request
			List<Friend> friendRequestList = fservice.getAllFriendRequest(umob);
			res.addAttribute("friendRequestList",friendRequestList);
			
			//Getting All Friends
			List<Friend> myFriends = fservice.getAllMyFriends(umob);
			res.addAttribute("friendDetailsList",myFriends);
			
		
			//Finally Return Friend HTML
			return "friend";
		}
	}
	
	@GetMapping("/sendrequest/{mob}")
	public String sendRequest(Model res, @PathVariable("mob") Long toMob,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		
		Long fromMob = Long.parseLong(usermobile);
		
		//Check already friend or friend request sent
		List<Friend> checkThisNumber = fservice.findThisNumber(toMob,fromMob);
		if(checkThisNumber.size() == 1) {
			res.addAttribute("message","Already Sent Request OR Already Your Friend");
			res.addAttribute("referLocation", "/friends");
			return "message";
		}else {
			//send the request
			
			Friend friendObj = new Friend();
			//finding name for frommobile
			Optional<User> findNameForFromMobileOptional = uservice.getSearchFriend(fromMob);
			User fnffm = findNameForFromMobileOptional.get();
			friendObj.setFromMobile(fromMob);
			friendObj.setFromName(fnffm.getName());
			
			//finding name for tomobile
			Optional<User> findNameForToMobileOptional = uservice.getSearchFriend(toMob);
			User fnftm = findNameForToMobileOptional.get();
			friendObj.setToMobile(toMob);
			friendObj.setToName(fnftm.getName());
			
			//save friendship into db
			fservice.saveRequest(friendObj);
			
			//send the response
			res.addAttribute("message","Sent Request Successfully 👍");
			res.addAttribute("referLocation", "/friends");
			return "message";
		}
		
	}
	
	@GetMapping("/acceptstatus/{mob}/{opr}")
	public String acceptStatus(Model res, @PathVariable("mob") Long fromMob, @PathVariable("opr") boolean opr,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			Long toMob = Long.parseLong(usermobile);
			if(opr==true) {
				fservice.AcceptRequest(fromMob,toMob);
				res.addAttribute("message","Request Approved 👍");
			}else if(opr == false) {
				fservice.deleteRequest(fromMob,toMob);
				res.addAttribute("message","Request Rejected 👎");
			}
			res.addAttribute("referLocation", "/friends");
			return "message";
		}
	}
	
	@GetMapping("/delete/{mob}")
	public String removeFriend(Model res,@PathVariable("mob") Long fmob,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			Long umob = Long.parseLong(usermobile);
			fservice.removeMyFriend(umob,fmob,true);
			res.addAttribute("message", "Your Friend Removed 😑");
			res.addAttribute("referLocation", "/friends");
			return "message";
		}
	}
}
