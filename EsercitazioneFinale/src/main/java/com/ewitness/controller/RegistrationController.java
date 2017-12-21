package com.ewitness.controller;

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
	public String save(@Valid User user,BindingResult bindingResult,Model model, RedirectAttributes redirectAttrs) {
		User userExists = userServ.findByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.hasErrors();
			redirectAttrs.addFlashAttribute("errormessage", "There is already a user registered with the email provided");
			return "redirect:/singin";
		}
		if(bindingResult.hasErrors()) {
			return "auth/registrationForm";
		}else {
			user.setRole(Role.ROLE_USER);
//			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
		log.info("--------"+user);
		redirectAttrs.addFlashAttribute("message", "User "+user.getName()+" was saved.");
		return "redirect:/singin";
		}
	}

}
