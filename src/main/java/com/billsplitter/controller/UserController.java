package com.billsplitter.controller;


import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.billsplitter.entity.Bill;
import com.billsplitter.entity.Friend;
import com.billsplitter.entity.Splitter;
import com.billsplitter.entity.User;
import com.billsplitter.exception.CustomException;
import com.billsplitter.service.BillService;
import com.billsplitter.service.FriendService;
import com.billsplitter.service.SplitterService;
import com.billsplitter.service.UserService;



@Controller
public class UserController {
	
	@Autowired
	UserService uservice;
	
	@Autowired
	FriendService fservice;
	
	@Autowired
	BillService bservice;
	
	@Autowired
	SplitterService sservice;
	
	//All Get Methods For User
	@GetMapping("/")
	public String home(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			return "login";
		}else {
			res.addAttribute("message", "You are already logged in");
			res.addAttribute("referLocation", "/dashboard");
			return "message";
		}
	}
	@GetMapping("/signin")
	public String signin(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			return "signin";
		}else {
			res.addAttribute("message","Logout First For Creating New Account");
			res.addAttribute("referLocation", "/dashboard");
			return "message";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpServletResponse response) {
		Cookie ck=new Cookie("usermobile","");   
        response.addCookie(ck); 
        return "redirect:/";
	}
	
	@GetMapping("/dashboard")
	public String dashboard(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			res.addAttribute("username",username);
			
			//Getting Amount To take
			List<Bill> iPayerList = bservice.getAllBillInWhichIPayer(umob);
			double countOwe = 0;
			for(int i=0;i<iPayerList.size();i++) {
				for(int j =0; j<iPayerList.get(i).getBillMember().size();j++) {
					if(iPayerList.get(i).getBillMember().get(j).isAmountStatus() == false) {
						countOwe += iPayerList.get(i).getBillMember().get(j).getSplitAmount();
					}
				}
			}
			res.addAttribute("countOwe", countOwe);
			
			
			//Getting Amount To Pay
			List<Splitter> takeMoneyList = sservice.findMyToPayList(umob);
			double countPay = 0;
			for(int i=0;i<takeMoneyList.size();i++) {
				if(takeMoneyList.get(i).isAmountStatus() == false) {
					countPay += takeMoneyList.get(i).getSplitAmount();
				}
			}
			res.addAttribute("countPay",countPay);
			
			
			
			//Getting All Friends
			List<Friend> myFriends = fservice.getAllMyFriends(umob);
			res.addAttribute("friendCount",myFriends.size());
			return "dashboard";
		}
	}
	
	
	
	//All Post Methods For User
	@PostMapping("/usersave")
	public String createUser(@ModelAttribute User obj,HttpServletResponse response,Model model) {
		try {
			model.addAttribute("message", uservice.saveTheUser(obj));
			model.addAttribute("referLocation", "/dashboard");
			Cookie cookie = new Cookie("usermobile", ""+obj.getMobile());
			response.addCookie(cookie);
			return "message";
		}catch(CustomException e) {
			model.addAttribute("message", e.getMessage());
			model.addAttribute("referLocation", "/signin");
			return "message";
		}

	}
	
	@PostMapping("/validateUser")
	public String validateUser(@ModelAttribute User obj,HttpServletResponse response,Model model) {
		List<User> validuser = uservice.getValidUser(obj.getMobile(),obj.getPassword());
		if(validuser.size() == 1) {
			Cookie cookie = new Cookie("usermobile", ""+validuser.get(0).getMobile());
			response.addCookie(cookie);
			return "redirect:/dashboard";
		}else{
			model.addAttribute("message", "Not Valid User ❌ OR User Not Exist ❗");
			model.addAttribute("referLocation", "/");
			return "message";
		}
	}
	
}
