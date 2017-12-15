package com.ewitness.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured( {"ROLE_USER"} )
public class UserController {
	
	@RequestMapping("/user/a")
	public String a() {
		
		return "user/NewFile";
	}

}
