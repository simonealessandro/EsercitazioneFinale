package com.ewitness.service;

import com.ewitness.domain.User;

public interface UserServ {
	
	public User findByEmail(String email);

}
