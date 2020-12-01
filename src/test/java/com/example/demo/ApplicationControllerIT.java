package com.example.demo;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


import com.example.demo.modal.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


//Junit Test is same Test Intigration
@SpringBootTest(classes = MyApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationControllerIT {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TestRestTemplate template;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@WithMockUser("/testUserSave")
	@Test
	    public void testAddUser() throws Exception {
	    	
	        User user = new User(50,"ahmad98","ahmad","mla",20,"123","mohammad.m.moazen@gmail.com",1,"programmer",true,50,1,"0992875193");
	       
	      //  String URIToCreateUser = "/savePerson";
	        setup();
		    String inputInJson = this.mapToJson(user);   //Here I convert The User Object into Json (String)
			
				System.out.println("to json"+inputInJson);
			MvcResult result = mockMvc.perform(post("/savePerson").content(inputInJson).contentType(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk()).andReturn();
			
			System.out.println("Header is:" + result.getHandler());
			System.out.println("The Response is : " + result.getModelAndView().getViewName());
			System.out.println("The Status is : " + result.getResponse().getStatus());
	        
	    }
	    
	    
	    
		  //this method Maps an Object into a JSON String. Uses a Jackson ObjectMapper.
		 
		private String mapToJson(Object object) throws JsonProcessingException {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}

	 

}
