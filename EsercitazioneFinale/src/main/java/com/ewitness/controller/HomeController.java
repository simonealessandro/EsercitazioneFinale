package com.ewitness.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
//	@Autowired
//	private UserService userService;	
//	@Autowired
//	private ReadExcel a;

	@RequestMapping("/")
	public String home(Model model){
//		model.addAttribute("user", userService.list());
		return "index";
	}
	
	@RequestMapping("/user")
	public String user(Model model){
//		model.addAttribute("user", userService.list());
		return "home/index_user";
	}
	
	@RequestMapping("/admin")
	public String admin(Model model){
//		model.addAttribute("user", userService.list());
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
