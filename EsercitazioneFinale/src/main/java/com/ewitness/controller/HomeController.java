package com.ewitness.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ewitness.service.UserService;
import com.ewitness.util.ReadExcel;

@Controller
public class HomeController {
	
	private UserService userService;
	
	@Autowired
	private ReadExcel a;
	
	
	public HomeController(UserService userService) {
	
		this.userService = userService;
	}


	@RequestMapping("/")
	public String home(Model model){
//		model.addAttribute("user", userService.list());
		return "index";
	}
	
//	@RequestMapping("/xls")
//	public String string() throws IOException{
////		model.addAttribute("user", userService.list());
//		String excelFilePath = "C:\\Users\\Simone\\Desktop\\c.xlsx";
//		a.read(excelFilePath);
//		return "index";
//	}
}
