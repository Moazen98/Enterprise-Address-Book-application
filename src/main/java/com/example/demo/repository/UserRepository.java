package com.example.demo.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {	
	
	public User findByUsernameAndPassword(String username, String password);
	
	public User findUserById(long id);
	
	public User findByUsername(String username);
	
	
	
}
