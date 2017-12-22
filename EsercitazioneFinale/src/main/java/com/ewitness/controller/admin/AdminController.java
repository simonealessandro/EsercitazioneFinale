package com.ewitness.controller.admin;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ewitness.service.UserService;

@Controller
public class AdminController {
		
	private UserService userService;
	
	@Autowired
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/admin/users")
	public String list(Model model) {
		model.addAttribute("users", userService.list());
		return "/admin/users/users_list";
	}
	
//	@RequestMapping("/admin/users/{id}")
//	public String view(@PathVariable Long id, Model model) {
//		model.addAttribute("user", userService.get(id));
//		return "admin/users/view";
//	}
//	@RequestMapping("/admin/users/create")
//	public String create(Model model) {
//		model.addAttribute("user", new User());
//		return "admin/user/adminForm";
//	}
//	@RequestMapping (value= "admin/users/save", method= RequestMethod.POST)
//	public String save(@Valid User user,BindingResult bindingResult,Model model) {
//		if(bindingResult.hasErrors()) {
//	//		model.addAttribute("users", userService.list());
//			return "admin/users/adminForm";
//		}else {
//		User savedUser=userService.save(user);
//		return "redirect:/admin/users/" + savedUser.getId();
//		}
//	}
	@RequestMapping("/admin/users/edit/{id}")
	public String edit(@PathVariable Long id,Model model) {
		model.addAttribute("user", userService.get(id));
		return "admin/users/adminForm";
		
	}
	
	@RequestMapping("/admin/users/delete/{id}")
	public String delete(@PathVariable Long id,RedirectAttributes redAttrs,Locale loc) {
		
		userService.delete(id);
		if(loc.getLanguage().equals("en"))
			redAttrs.addFlashAttribute("message","User "+id+" was deleted!");
		if(loc.getLanguage().equals("it"))
			redAttrs.addFlashAttribute("message","L'utente "+id+" è stato eliminato!");
		return "redirect:/admin/users/";
	}
}
