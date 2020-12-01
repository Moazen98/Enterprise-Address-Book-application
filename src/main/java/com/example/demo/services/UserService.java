package com.example.demo.services;

import java.io.File ; 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Aspict.MyAnno;
import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRolesRepository;

import java.sql.Statement;

@Service
@Transactional
public class UserService {

	private final UserRepository userRepository;
	private final UserRolesRepository userRolesRepository;
	static Connection Mycon = null;
	static String Myurl = "jdbc:mysql://localhost:3306/datasystemcenter";
	static String username = "root";
	static String password = "123456";
	Statement staement;

	public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
		this.userRepository = userRepository;
		this.userRolesRepository = userRolesRepository;
	}

	
	@MyAnno
	public void saveMyUser(User user) {
		userRepository.save(user);
	}

	public User saveMyUserTest(User user) {
		return userRepository.save(user);
	}
	
	
	
	@Transactional
	public List<User> showAllUsers() {
		List<User> users = new ArrayList<User>();
		// List<User_Roles> usersRole = new ArrayList<User_Roles>();
		for (User user : userRepository.findAll()) {
			users.add(user);
		}
		/*
		 * for(User_Roles userRole: userRolesRepository.findAll()) {
		 * usersRole.add(userRole); } for(int i=0;i<usersRole.size();i++)
		 * System.out.println("User roles is:"+usersRole.get(i));
		 */
		return users;
	}
	

	@Transactional
	public boolean userExisit(String username) {
		
		User user = userRepository.findByUsername(username);
		
		if(user!=null) {
			System.out.println("The user is here:"+user.getUsername());
			return true;
		}else {
			System.out.println("The user is not here");
			return false;
		}
	}


	
	@Transactional
	public List<User_Roles> showAllUsersRole() {
		List<User_Roles> usersRole = new ArrayList<User_Roles>();

		for (User_Roles userRole : userRolesRepository.findAll()) {
			usersRole.add(userRole);
		}
		for (int i = 0; i < usersRole.size(); i++)
			System.out.println("User roles is:" + usersRole.get(i));
		return usersRole;
	}


	
	@Transactional
	public List<User_Roles> showAllUsersRoleDept(List<User> userDep) {
		List<User_Roles> usersRole = new ArrayList<User_Roles>();

		for (User_Roles userRole : userRolesRepository.findAll()) {
			for (int i = 0; i < userDep.size(); i++) {
				if (userRole.getUsername().equals(userDep.get(i).getUsername())) {
					usersRole.add(userRole);
				}
			}
		}
		for (int i = 0; i < usersRole.size(); i++)
			System.out.println("User roles is:" + usersRole.get(i));
		return usersRole;
	}
	
	@Transactional
	public User_Roles findUserRole(String username) {
		User_Roles userRole = new User_Roles();
		userRole = userRolesRepository.findByUsername(username);
		
		return userRole;
	}

	@Transactional
	public void deleteMyUser(long id) {
		userRepository.deleteById(id);
	}

	@Transactional
	public void deleteMyUserRole(long id) {
		userRolesRepository.deleteById(id);
	}

	
	@Transactional
	public User editUser(long id) {
		return userRepository.findUserById(id);
	}

	
	@Transactional
	public User_Roles editUserRole(long id) {
		return userRolesRepository.findUser_RolesById(id);
	}

	
	
	@Transactional
	public int updating(User user)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		int sqlRes;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Mycon = DriverManager.getConnection(Myurl, username, password);
		staement = Mycon.createStatement();
		
		
		sqlRes = staement.executeUpdate(" update userstable set id='" + user.getId() + "', username='" + user.getUsername()
				+ "'," + "firstname='" + user.getFirstname() + "',lastname='" + user.getLastname() + "' ," + "age='"
				+ user.getAge() + "',password='" + user.getPassword() + "',email='" + user.getEmail() + "',phone='"
				+ user.getPhone() + "',accepted='" + user.getAccepted() + "',enabled='" + user.isEnabled()
				+ "', department='" + user.getDepartment() + "' ,version='" + (user.getVersion() + 1) + "' where id='"
				+ user.getId() + "' and version ='" + user.getVersion() + "'   ");

		return sqlRes;
	}


	@Transactional
	public int updatingRole(User_Roles user)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		int res;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Mycon = DriverManager.getConnection(Myurl, username, password);
		staement = Mycon.createStatement();

		res = staement.executeUpdate(" update user_roles set id='" + user.getId() + "', permissions='" + user.getPermissions()
				+ "',roles ='"+user.getRoles()+ "',username='"+ user.getUsername()+ "',phone ='"+user.getPhone() +"',version ='" + (user.getVersion() + 1) + "' where id='"
				+ user.getId() + "' and version  ='" + user.getVersion() + "'   ");
		
		
		return res;
	}


	public User findByUsernameAndPassword(String username, String password) {
		System.out.println("The user is: in findByUsernameAndPassword" + username + "The password is:" + password);

		return userRepository.findByUsernameAndPassword(username, password);
	}

	public User findByUsernameMethod(String name) {

		User user = new User();
		user = userRepository.findByUsername(name);
		return user;
	}

	
	//Not complete
	// MultipartFile sign to the file that the user upload
	@Transactional
	public void upload(MultipartFile file) throws IOException, EncryptedDocumentException, InvalidFormatException {

		Path tempDir = Files.createTempDirectory("");

		@SuppressWarnings("unused")
		File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();

		Workbook workbook = WorkbookFactory.create(tempFile);

		// get the sheet number
		Sheet sheet = workbook.getSheetAt(0);

		// Make Stream of Rows
		Stream<Row> rowStream = StreamSupport.stream(sheet.spliterator(), false);

		rowStream.forEach(row -> {
			// here i give a row and get CellStream from it

			Stream<Cell> cellStream = StreamSupport.stream(row.spliterator(), false);

			List<String> cellVals = cellStream.map(cell -> {

				String cellVal = cell.getStringCellValue(); // For String Value
				return cellVal;
			}).collect(Collectors.toList()); // here put the output in list

			System.out.println(cellVals);
		});

	}


	@Transactional
	public void SaveUserFullDetails(User_Roles userRoles) {
		userRolesRepository.save(userRoles);
	}
	
	
	


	
	

	@Transactional
	public List<User> showAllAcceptedUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : userRepository.findAll()) {
			if (user.getAccepted() == 1)
				users.add(user);
		}
		return users;
	}

	
	@Transactional
	public List<User> showAllAcceptedUsersDept(String dept) {

		List<User> users = new ArrayList<User>();
		for (User user : userRepository.findAll()) {
			// && user.getDepartment().equals(arg0)
			if (user.getAccepted() == 1 && user.getDepartment().equals(dept))
				users.add(user);
		}
	
		return users;
	}

	@Transactional
	public List<User> showAllNonAcceptedUsers() {
		List<User> users = new ArrayList<User>();
		for (User user : userRepository.findAll()) {
			if (user.getAccepted() == 0)
				users.add(user);
		}
		return users;
	}

	@Transactional
	public List<User_Roles> showAllAdmins() {
		List<User_Roles> userRole = new ArrayList<User_Roles>();
		for (User_Roles user : userRolesRepository.findAll()) {
			if (user.getPermissions().equals("ADMIN"))
				userRole.add(user);
		}
		return userRole;
	}

}
