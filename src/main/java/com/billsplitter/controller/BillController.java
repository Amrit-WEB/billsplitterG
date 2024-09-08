package com.billsplitter.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.billsplitter.entity.Bill;
import com.billsplitter.entity.Friend;
import com.billsplitter.entity.Splitter;
import com.billsplitter.entity.User;
import com.billsplitter.service.BillService;
import com.billsplitter.service.FriendService;
import com.billsplitter.service.SplitterService;
import com.billsplitter.service.UserService;

@Controller
public class BillController {
	
	@Autowired
	UserService uservice;
	
	@Autowired
	FriendService fservice;
	
	@Autowired
	BillService bservice;
	
	@Autowired 
	SplitterService sservice;
	
	
	@GetMapping("/managebill")
	public String managebill(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			res.addAttribute("username",username);
			res.addAttribute("usermobile", umob);
			
			//getMyFriend for Payer Option
			List<Friend> payerOption = fservice.getAllMyFriends(umob);
			res.addAttribute("payerOption", payerOption);
			
			
			return "managebill";
		}
	}
	
	@GetMapping("/mybill")
	public String mybill(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			res.addAttribute("username",username);
			res.addAttribute("usermobile", umob);
			
			List<Bill> myBillList = bservice.getAllMyBill(umob);
			res.addAttribute("myBillList", myBillList);
			
			return "mybill";
		}
	}
	
	
	@GetMapping("/settlebill")
	public String settlebill(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			res.addAttribute("username",username);
			res.addAttribute("usermobile", umob);
			
			//sending settlement bill
			
			//This approach need to more work on bidirectional one to many mapping
//			List<Splitter> toPayList = sservice.findMyToPayList(umob);
//			res.addAttribute("payList", toPayList);
			
			//I'm Going to use fetch all bill and filter them with condition
			
			//1----Fetch All Bill
			List<Bill> allBill = bservice.getAllBill();
			//2----Creating this blank array for sending to UI
			List<Bill> payList = new ArrayList<>();
			//3----Filteration
			for(int i=0;i<allBill.size();i++) {
				//my bill object because i only want 1 bill with 1 member not all member
				Bill bobj = new Bill();
				bobj.setBillAmount(allBill.get(i).getBillAmount());
				bobj.setBillCreator(allBill.get(i).getBillCreator());
				bobj.setBillDate(allBill.get(i).getBillDate());
				bobj.setBillId(allBill.get(i).getBillId());
				bobj.setBillName(allBill.get(i).getBillName());
				bobj.setBillPayer(allBill.get(i).getBillPayer());
				bobj.setBillPayerName(allBill.get(i).getBillPayerName());
				
				List<Splitter> member = allBill.get(i).getBillMember();
				//One Member filteration
				for(int j=0;j<member.size();j++) {
					if(member.get(j).getMemberMobile()==umob) {
						
						List<Splitter> oneMember = new ArrayList<>();
						Splitter sobj = new Splitter();
						sobj.setId(member.get(j).getId());
						sobj.setAmountStatus(member.get(j).isAmountStatus());
						sobj.setSplitAmount(member.get(j).getSplitAmount());
						sobj.setMemberName(member.get(j).getMemberName());
						sobj.setMemberMobile(member.get(j).getMemberMobile());
						oneMember.add(sobj);
						
						bobj.setBillMember(oneMember);
						//adding payList
						payList.add(bobj);
					}
				}
			}
			
			res.addAttribute("payList", payList);
			
			return "settlebill";
		}
	}
	
	@GetMapping("/settle/{sid}")
	public String paid(Model res,@PathVariable("sid") int sid,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			sservice.settleMyBill(sid);
			res.addAttribute("message", "Amount Paid ✌️");
			res.addAttribute("referLocation", "/dashboard");
			return "message";
		}
	}
	
	
	@GetMapping("/receivebill")
	public String receivebill(Model res,@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(usermobile.equals("Atta")) {
			res.addAttribute("message","Unauthorized Aceess 🚫");
			res.addAttribute("referLocation", "/");
			return "message";
		}else {
			//Sending UserName from Cookies UserMobile
			Long umob = Long.parseLong(usermobile);
			String username = uservice.getUserName(umob);
			res.addAttribute("username",username);
			res.addAttribute("usermobile", umob);
			
			List<Bill> asPayerBillList = bservice.getAllBillInWhichIPayer(umob);
			res.addAttribute("myBillList", asPayerBillList);
			
			return "receivebill";
		}
	}
	
	
	
	
	
	
	@PostMapping("/savebill")
	public String savebill(Model res,@ModelAttribute Bill billobj,@Param(value="memberList") Long memberList[],@CookieValue(value = "usermobile", defaultValue = "Atta") String usermobile) {
		if(memberList == null) {
			res.addAttribute("message", "Please Add At Least One Bill Member");
			res.addAttribute("referLocation","/managebill");
			return "message";
		}else {
			//Calculating Split amount and converting into 2 decimal format
			DecimalFormat df = new DecimalFormat("#.00");
			double splitamt = billobj.getBillAmount() / (memberList.length + 1);
			String splitamtFormated = df.format(splitamt);
			
			
			//inserting bill creator in bill object
			billobj.setBillCreator(Long.parseLong(usermobile));
			
			//finding Payer name
			Optional<User> findNameForPayerOptional = uservice.getSearchFriend(billobj.getBillPayer());
			User fnfp = findNameForPayerOptional.get();
			billobj.setBillPayerName(fnfp.getName());
			
			//insert blank Array in bill member
			billobj.setBillMember(new ArrayList<>());
			//filling array for bill member
			for(int i=0;i<memberList.length;i++) {
				Splitter sp = new Splitter();
				//insert mobile of member into splitter object
				sp.setMemberMobile(memberList[i]);
				//find name for that member
				Optional<User> findNameForMemberOptional = uservice.getSearchFriend(memberList[i]);
				User fnfm = findNameForMemberOptional.get();
				//insert name into splitter object
				sp.setMemberName(fnfm.getName());
				//insert split amount into splitter object
				sp.setSplitAmount(Double.parseDouble(splitamtFormated));	
				//now adding one member detail in bill member 
				billobj.getBillMember().add(sp);
			}
			
			bservice.saveBill(billobj);
			res.addAttribute("message", "Bill Created Successfully ✅");
			res.addAttribute("referLocation","/mybill");
			return "message";
		}
		
	}
	
	@PostMapping("/deletebill")
	public String deleteBill(Model res,@Param(value="bid") int bid,@CookieValue(value = "usermobile", defaultValue = "Atta")String usermobile) {
		Long delcount = bservice.removeBill(bid,Long.parseLong(usermobile));
		
		if(delcount==0) {
			res.addAttribute("message","Oops! Bill Not Found 😐");
		}else {
			res.addAttribute("message","Bill Deleted Succefully ✅");			
		}
		res.addAttribute("referLocation","/managebill");
		return "message";
	}
}
