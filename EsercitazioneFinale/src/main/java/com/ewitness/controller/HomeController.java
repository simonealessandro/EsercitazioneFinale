package com.ewitness.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewitness.service.UserService;

@Controller
public class HomeController {
	
	private UserService userService;
	
	
	public HomeController(UserService userService) {
	
		this.userService = userService;
	}


	@RequestMapping("/")
	public String home(Model model){
//		model.addAttribute("user", userService.list());
		return "index";
	}
	
}
