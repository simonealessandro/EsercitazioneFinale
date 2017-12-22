package com.ewitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
		
	@RequestMapping("/")
	public String user(Model model){
		return "index";
	}
	
	@RequestMapping("/user")
	public String user(){
		return "home/index_user";
	}
	
	@RequestMapping("/admin")
	public String admin(Model model){
		return "admin/home";
	}
	
//	@RequestMapping("/xls")
//	public String string() throws IOException{
////		model.addAttribute("user", userService.list());
//		String excelFilePath = "C:\\Users\\Simone\\Desktop\\c.xlsx";
//		a.read(excelFilePath);
//		return "index";
//	}
}
