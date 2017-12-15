package com.ewitness.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewitness.domain.User;
import com.ewitness.service.UserServiceImpl;

@Controller
public class HomeController {
	
	private UserServiceImpl us;

	@Autowired
	public HomeController(UserServiceImpl us) {
		super();
		this.us = us;
	}
	
//	@Secured( {"ROLE_ADMIN"} )
//	@RequestMapping("/admin/users")
//	public String list(Model model) {
//		model.addAttribute("users", us.list());
//		return "user/list";
//	}
	
	@RequestMapping("/")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "auth/userForm";
	}
	
	@RequestMapping( value = "/save", method = RequestMethod.POST )
	public String save(@Valid User user, BindingResult bindingResult, Model model,  RedirectAttributes redirectAttrs) {
		
		if( bindingResult.hasErrors() ) {
			return "auth/userForm";
		} else {
			us.save(user);
			redirectAttrs.addFlashAttribute("message", "User was created!");
			return "redirect:/";
		}
		
	}
	
	@RequestMapping(value="/user/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = us.findByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}

//	@Secured( {"ROLE_ADMIN"} )
//	@RequestMapping("/admin/delete/{id}")
//	public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
//		us.delete(id);
//		redirectAttrs.addFlashAttribute("message", "User was deleted!");
//		return "redirect:/admin/user";
//	}


}
