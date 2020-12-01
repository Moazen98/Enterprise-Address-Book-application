package com.example.demo.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "userstable")
public class  User implements Serializable {

	@Id
	private long id;
	private String username;
	private String firstname;
	private String lastname;
	private int age;
	private String password;
	private String department;
	private boolean enabled;
	private int user_role_id;
	@Version //delete it if it error
	private int version;
	
	private String email;
	private int accepted;

	private String phone;

	@ManyToOne
	@JoinColumn
	public User_Roles user_roles;

	public User() {

	}

	public User(String username, String firstname, String lastname, int age, String password, String department,
			int enable, String role, String permissions) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.password = password;
		this.department = department;
		this.enabled = true;
		// this.role = role;
		// this.permissions = permissions;
	}

	public User(long id, String username, String firstname, String lastname, int age, String password,
			String department, boolean enabled, String role, String permissions, int version) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.password = password;
		this.department = department;
		this.enabled = enabled;
		// this.role = role;
		// this.permissions = permissions;
		this.version = version;
	}

	public User(long id, String username, String firstname, String lastname, int age, String password, String email,
			int accepted, String department, boolean enabled, int user_role_id, int version, String phone) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
		this.password = password;
		this.department = department;
		this.enabled = enabled;
		this.user_role_id = user_role_id;
		this.version = version;
		this.email = email;
		this.accepted = accepted;
		this.phone = phone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/*
	 * public String getRole() { return role; }
	 * 
	 * public void setRole(String role) { this.role = role; }
	 * 
	 * public String getPermissions() { return permissions; }
	 * 
	 * public void setPermissions(String permissions) { this.permissions =
	 * permissions; }
	 */

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}

	public User_Roles getUser_roles() {
		return user_roles;
	}

	public void setUser_roles(User_Roles user_roles) {
		this.user_roles = user_roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAccepted() {
		return accepted;
	}

	public void setAccepted(int accepted) {
		this.accepted = accepted;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int SizeUserList() {
		return user_roles.users.size();
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", age=" + age + ", password=" + password + ", department=" + department + ", enabled=" + enabled
				+ ", user_role_id=" + user_role_id + ", version=" + version + ", email=" + email + ", accepted="
				+ accepted + ", phone=" + phone + ", user_roles=" + user_roles + "]";
	}

	/*
	 * @Override public String toString() { return "User [id=" + id + ", username="
	 * + username + ", firstname=" + firstname + ", lastname=" + lastname + ", age="
	 * + age + ", password=" + password + ", department=" + department +
	 * ", enabled=" + enabled + ", role=" + role + ", permissions=" + permissions +
	 * ", version=" + version + "]"; }
	 * 
	 * public List<String> getPermissionList() { if (this.permissions.length() > 0)
	 * { return Arrays.asList(this.permissions.split(",")); } return new
	 * ArrayList<>(); }
	 * 
	 * public List<String> getRoleList() { if (this.role.length() > 0) { return
	 * Arrays.asList(this.role.split(",")); } return new ArrayList<>(); }
	 */

}
