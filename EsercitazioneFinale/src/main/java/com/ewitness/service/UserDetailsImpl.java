package com.ewitness.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ewitness.domain.User;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 3185970362329652822L;
	
	private User user;
	
	public UserDetailsImpl(User user){
		this.user = user;
	}
	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//		Set<Role> roles = User.getRoles();
//		for( Role role : roles ) {
//			authorities.add( new SimpleGrantedAuthority(role.getRole()) ); 
//		}
//		return authorities;		
//	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

}
