package com.example.demo;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.constraints.AssertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;
import com.example.demo.repository.UserRolesRepository;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.ComparisonFailure;

@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessUserTI {

	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;



	@Autowired
	UserService userService;

	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@WithMockUser("/testSecureLog")
	@Test
	public void userLogin() throws Exception {

		setup();
		User user = new User(37, "zaher99", "mohamad", "al moazen", 21, "$2a$10$PDxK6D6a8tJmrnhDgXVIl.9FtSyp/UvRTubEZOs4DzifFHPXf2QbC", "mohammad.m.moazen@gmail.com", 1, "Networking", true,
				0, 1, "0992875193");

		  String toJason = mapper.writeValueAsString(user);
			System.out.println("to json"+toJason);
		MvcResult result = mockMvc.perform(post("/LogPerson").content(toJason).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		
		System.out.println("Header is:" + result.getHandler());
		System.out.println("The Response is : " + result.getModelAndView().getViewName());
		System.out.println("The Status is : " + result.getResponse().getStatus());
		assertEquals("welcomepage",result.getModelAndView().getViewName());

			if(result.getModelAndView().getViewName().equals("welcomepage")) { // if the user is logged
				System.out.println("Welcome :"+user.getUsername());
				System.out.println("The user Department is:"+ user.getDepartment());
				assertEquals(200, result.getResponse().getStatus()); 
				System.out.println("The Status of the Login Opt is :"+result.getResponse().getStatus());
				userRole(user,result.getResponse().getStatus());
			}
		
	}
	
	// this method is just to bring user roles
	public void userRole(User user,int status) throws Exception {
	  	
		if(status == 200) {
		 User_Roles userroleRepo = new User_Roles();
		 System.out.println("username :"+user.getUsername());
		 userroleRepo = userService.findUserRole(user.getUsername()); //here we bring the User loged Role
		 String userRole = userroleRepo.getRoles();
		 System.out.println("userRole from Repo :"+ userroleRepo);
		 System.out.println("The user role is :"+userRole);
		
		 testSecureSet(user.getUsername(),user.getPassword(),userRole,user.getDepartment());
		
		}

			
	}

	//this method is just to test the role acceess to spicific page
	@WithMockUser("/testSecureInt")
	public void testSecureSet(String username,String userPassword,String userRole,String dept) throws Exception {
		
		System.out.println("userPassword is "+userPassword);
		System.out.println("userDept is "+dept);
		mockMvc.perform(post("/show-users-test").content(userRole)
				.accept("application/json")  //here we set the Accept for header
                .contentType("application/json")  //here we set the contant type of the header
				.with(csrf())
				.with(user(username).roles(userRole))) //here we spicific the user that we want to make testing on it
		.andDo(MockMvcResultHandlers.print()) //print the trace of the operation for tracing
		.andExpect(status().isOk()).andReturn().equals(200); // if the test is success

	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
