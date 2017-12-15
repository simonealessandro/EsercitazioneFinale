package com.ewitness.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService);
	}


	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.authorizeRequests()
//				.antMatchers("/admin/**").hasRole("ADMIN")
//				.anyRequest().permitAll()
//				.and()
//			.formLogin()
//				.loginPage("/login")
//				.permitAll()
//				.and()
//			.logout()
//				.logoutSuccessUrl("/login?logout")
//				.permitAll();
//	}	
//		.authorizeRequests()
//		.antMatchers("/admin").hasRole("ADMIN")
//		.anyRequest().permitAll()
//		.and()
	.authorizeRequests()
		.antMatchers("/user/**").hasRole("USER")
		.anyRequest().permitAll()
		.and()
	.formLogin()
		.loginPage("/login")
		.usernameParameter("email")
		.permitAll()
		.and()
	.logout()
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}
}


