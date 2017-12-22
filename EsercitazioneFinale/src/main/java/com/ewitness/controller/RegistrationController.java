package com.ewitness.controller;

import java.util.Locale;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ewitness.domain.User;
import com.ewitness.domain.User.Role;
import com.ewitness.repository.UserRepository;
import com.ewitness.service.UserService;

@Controller
public class RegistrationController {
	
	Logger log = Logger.getLogger(RegistrationController.class.getName());

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userServ;

	@RequestMapping("/singin")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "auth/registrationForm";
	}
	@RequestMapping (value= "/singin/save", method= RequestMethod.POST)
	public String save(@Valid User user,BindingResult bindingResult,Model model, RedirectAttributes redirectAttrs, Locale loc) {
		User userExists = userServ.findByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.hasErrors();
			if(loc.getLanguage().equals("en"))
				redirectAttrs.addFlashAttribute("errormessage", "There is already a user registered with the email provided");
			if(loc.getLanguage().equals("it"))
				redirectAttrs.addFlashAttribute("errormessage", "Sei già registrato con questa email");
			return "redirect:/singin";
		}
		if(bindingResult.hasErrors()) {
			return "auth/registrationForm";
		}else {
			user.setRole(Role.ROLE_USER);
//			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
		log.info("--------"+user);
		if(loc.getLanguage().equals("en"))
			redirectAttrs.addFlashAttribute("message", "User "+user.getName()+" was saved.");
		if(loc.getLanguage().equals("it"))
			redirectAttrs.addFlashAttribute("message", "L'utente "+user.getName()+" è stato salvato.");
		return "redirect:/singin";
		}
	}
	@RequestMapping (value= "/singin/update", method= RequestMethod.POST)
	public String update(@Valid User user,BindingResult bindingResult,Model model, RedirectAttributes redirectAttrs, Locale loc) {
		if(bindingResult.hasErrors()) {
			return "auth/registrationForm";
		}else {
			user.setRole(Role.ROLE_USER);
//			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
		log.info("--------"+user);
		if(loc.getLanguage().equals("en"))
			redirectAttrs.addFlashAttribute("message", "User "+user.getName()+" was saved.");
		if(loc.getLanguage().equals("it"))
			redirectAttrs.addFlashAttribute("message", "L'utente "+user.getName()+" è stato salvato.");
		return "redirect:/singin";
		}
	}
}
