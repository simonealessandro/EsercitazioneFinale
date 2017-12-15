//package com.ewitness.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import com.ewitness.domain.User;
//import com.ewitness.domain.User.Role;
//import com.ewitness.repository.UserRepo;
//
//@Service
//public class UserServ {
//
//	private UserRepo userRepo;
//	
//	@Autowired
//	public UserServ (UserRepo userRepo) {
//		this.userRepo=userRepo;
//	}
//	
//	public List<User> list(){
//		return userRepo.findAll();
//	}
//	
//	
//	public User get(Long id) {
//		return userRepo.findOne(id);
//	}
//	
//	public void delete (Long id) {
//		userRepo.delete(id);
//	} 
//	
//	public User save (User user) {
//		//user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//		user.setRole(Role.ROLE_USER);
//		return userRepo.save(user);
//	}
//	
//	
//}
