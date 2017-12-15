package com.ewitness.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ewitness.domain.User;

@Repository("userRepository")
public interface UserRepo extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	List<User> findAll();

}
