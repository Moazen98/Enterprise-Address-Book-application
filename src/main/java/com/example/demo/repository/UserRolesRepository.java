package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;


public interface UserRolesRepository extends CrudRepository<User_Roles, Long> {

	User_Roles findByUsername(String username);


	public User_Roles findUser_RolesById(long id);
	
	

}
