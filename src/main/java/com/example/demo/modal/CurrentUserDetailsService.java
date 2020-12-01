package com.example.demo.modal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.demo.services.UserService;


public class CurrentUserDetailsService implements UserDetailsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);
	private final UserService userService;

	@Autowired
	public CurrentUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override

	public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("Authenticating user with username={}");
		User user = userService.findByUsernameMethod(username);
		return new CurrentUser(user);
	}
	

}
