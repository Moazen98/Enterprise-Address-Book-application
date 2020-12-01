package com.example.demo.modal;

import com.example.demo.modal.User;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "user_roles")
public class User_Roles implements Serializable{

	@Id
	private long id;
	private String roles;
	private String permissions;
	private String username;
	// here you must user Set and fetch = FetchType.EAGER for the  failed to lazily initialize
	//EAGER loading of collections means that they are fetched fully at the time their parent is fetched
	//LAZY not load the all of objects if you dont need it
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user_roles", cascade = CascadeType.ALL)
	public Set<User> users = new HashSet<>();
	private String phone;
	private int version;

	public User_Roles(long id, String roles, String permissions, User newuser, String username, String phone,int version) {
		this.id = id;
		this.roles = roles;
		this.permissions = permissions;
		users.add(newuser);
		this.username = username;
		this.phone = phone;
		this.version = version;
	
	}

	public User_Roles(long id, String roles, String permissions, String username) {
		this.id = id;
		this.roles = roles;
		this.permissions = permissions;
		this.username = username;

	}

	public User_Roles() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String role) {
		this.roles = role;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
	 public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<String> getPermissionList() { if (this.permissions.length() > 0)
		  { return Arrays.asList(this.permissions.split(",")); } return new
		  ArrayList<>(); }
		  
		  public List<String> getRoleList() { if (this.roles.length() > 0) { return
		  Arrays.asList(this.roles.split(",")); } return new ArrayList<>(); }

		@Override
		public String toString() {
			return "User_Roles [id=" + id + ", roles=" + roles + ", permissions=" + permissions + ", username="
					+ username + ", users=" + users + ", phone=" + phone + ", version=" + version + "]";
		}
		 
	
	

}
