package com.ewitness.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ewitness.domain.User;
import com.ewitness.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	public List<User> list() {
		return userRepository.findAll();
	}

	public User get(Long id) {
		return userRepository.findOne(id);
	}
	public User save(User user) {
		
		return userRepository.save(user);
		
	}
	public void delete(long id) {
		
		userRepository.delete(id);
	}
	
}
