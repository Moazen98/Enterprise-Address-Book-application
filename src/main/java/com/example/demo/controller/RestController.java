package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.modal.User;
import com.example.demo.services.UserService;

//@RestController annotation in Spring MVC
//is a combination of @Controller and @ResponseBody annotation.

//@Controller is to create a Map of model object and find a view 
//but @RestController simply return the object and object data is directly written into HTTP response as JSON or XML.

//the RestController not need to a html page to return it can creat by it own 

@org.springframework.web.bind.annotation.RestController
public class RestController {
	
//	@Autowired
//	private UserService userService;

	@GetMapping("/")
	public String hello() {
		return "This is Home page for Testing the RestController";
	}
	
//	@GetMapping("/saveuser")
//	public String saveUser(@RequestParam String username, @RequestParam String firstname, @RequestParam String lastname, @RequestParam int age, @RequestParam String password) {
//		User user = new User(username,firstname,lastname,age,password,"NO",1,"Standard","Standard");
//		userService.saveMyUser(user);
//		return "User Saved";
//	}
}
