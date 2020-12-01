package com.example.demo.Principal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;

/*
//I add this for autowiring in WebSecuroty
@Service   
public class UserPrincipalDetailsService implements UserDetailsService {

	private UserRepository userRepository;
	
	

	public UserPrincipalDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqq");
		User user = this.userRepository.findByUsername(username);  // here ya basha i get a specific user form my repo
	   
	    UserPrincipal userPrincipal = new UserPrincipal(user); //here i convert the user to type of principle data
		return userPrincipal;
	}

}

*/
