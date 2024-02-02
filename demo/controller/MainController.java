package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.User;
import com.example.demo.service.AccountService;
import com.example.demo.service.MainService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MainController {
	@Autowired
	MainService service;
	@Autowired
	AccountService service2;
	
	@GetMapping("/")
	public String home() {
		return "home.jsp";
	}
	@PostMapping("register")
	public String regsiter(User user, HttpSession session, HttpServletRequest request) {
		if (user.getFull_name() == null || user.getFull_name().isEmpty()) {
	        return "register.jsp";
	    }
		if(service.isExists(user.getEmail())) {
			request.setAttribute("error", "error");
			return "register.jsp";
		}
		else {
			String email = user.getEmail();
			session.setAttribute("loggedInUserEmail", email);
			service.createUser(user);
			return "openaccount.jsp";
		}
	}
	@PostMapping("login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, HttpServletRequest request) {
		session.removeAttribute("error");
		if(service.isExists(email, password)) {
			session.setAttribute("loggedInUserEmail", email);
			if(service2.isExists(email)) {
				int amount = service2.getAmountByEmail(email);
				 long ac_number = service2.getAccountNumberByEmail(email);
			       session.setAttribute("number",ac_number);
				session.setAttribute("value", amount);
				return "mainpage.jsp";
			}
			else {
				return "openaccount.jsp";
			}
		}
		else {
			request.setAttribute("error", "error");
			return "login.jsp";
		}
	}
	@PostMapping("openaccount")
	public String openAccount(@RequestParam("name") String name, @RequestParam("initial")int amount, @RequestParam("pin") String pin, HttpSession session, HttpServletRequest request) {
	    String email = (String) session.getAttribute("loggedInUserEmail");
	    if (email != null) {
	        service2.createAccount(email, name, amount, pin);
	        long ac_number = service2.getAccountNumberByEmail(email);
	        session.setAttribute("number",ac_number);
	        session.setAttribute("value", amount);
	        return "mainpage.jsp";
	    } else {
	    	request.setAttribute("error", "error");
	        return "openaccount.jsp";
	    }
	}

	@PostMapping("debit")
	public String debit(@RequestParam("amount") int amount, @RequestParam("pin") String pin, HttpSession session, HttpServletRequest request) {
		String email = (String) session.getAttribute("loggedInUserEmail");
		int value = service2.debit(email, pin, amount);
		if(value >= 0) {
			session.setAttribute("value", value);
			return "mainpage.jsp";
		}
		
		else if(value == -1){
			return "error2.jsp";
		}
		else {
			return "error1.jsp";
		}
	}
	@PostMapping("credit")
	public String credit(@RequestParam("amount")int amount, @RequestParam("pin") String pin, HttpSession session, HttpServletRequest request) {
		String email = (String) session.getAttribute("loggedInUserEmail");
		int value = service2.credit(email, pin, amount);
		session.setAttribute("value", value);
		if(value >= 0) {
			return "mainpage.jsp";
		}
		else {
			return "error1.jsp";
		}
	}
	@PostMapping("transfer")
	public String transfer(@RequestParam("account_number") long account_number, @RequestParam("pin") String pin, @RequestParam("amount") int amount, HttpSession session, HttpServletRequest request) {
		String email = (String) session.getAttribute("loggedInUserEmail");
		int value = service2.transfer(email, account_number, pin, amount);
		if(value >= 0) {
			session.setAttribute("value", value);
			return "mainpage.jsp";
		}

		else if(value == -1){
			return "error2.jsp";
		}
		else {
			request.setAttribute("error", "-2");
			return "error1.jsp";
		}
	}
}
