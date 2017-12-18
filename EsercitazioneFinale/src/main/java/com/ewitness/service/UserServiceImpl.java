package com.ewitness.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ewitness.domain.User;
import com.ewitness.repository.UserRepository;

@Service
public class UserServiceImpl implements UserServ, UserDetailsService  {


	private UserRepository userRepo;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo){
		this.userRepo = userRepo;
	}


	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);
		if( user == null ){
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(user);
	}
	
	public List<User> list(){
		return userRepo.findAll();
	}
	
	
	public User get(Long id) {
		return userRepo.findOne(id);
	}
	
	public void delete (Long id) {
		userRepo.delete(id);
	} 
	
//	public User save (User user) {
//		//user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//		user.setRole(Role.ROLE_USER);
//		return userRepo.save(user);
//	}

}
