package com.example.demo.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Aspict.MyAnno;
import com.example.demo.MessageQueue.RabbitMQSender;
//import com.example.demo.MessageQueue.RabbitMQSender;
import com.example.demo.Security.WebSecurityConfig;
import com.example.demo.modal.CurrentUser;
import com.example.demo.modal.CurrentUserDetailsService;
import com.example.demo.modal.User;
import com.example.demo.modal.User_Roles;
import com.example.demo.repository.UserRolesRepository;
import com.example.demo.services.UserService;
import com.example.demo.view.ExcelUserListReportRoleView;
//import com.example.demo.view.ExcelPOIHelper;
import com.example.demo.view.ExcelUserListReportView;
import com.example.demo.view.PDFUserListReportRoleView;
import com.example.demo.view.PDFUserListReportView;

@Controller
@SessionAttributes("name") // the way number two for session
public class ApplicationController {

	@Autowired
	UserService userService;
	UserRolesRepository userRolesRepository;

	private String fileLocation;

	@Autowired
	public JavaMailSender javaMailSender;

	// here we use it if we have just one queue
	@Autowired
	RabbitMQSender rabbitMQSender;

	// here we use it if we have more than one queue
	@Autowired
	private AmqpTemplate amqpTemplate;

	@RequestMapping("/welcome")
	public String Welcome(HttpServletRequest request) {

		return "welcomepage";
	}

	@RequestMapping("/register")
	public String registration(HttpServletRequest request) {
		System.out.println("i am regster 0");

		return "Register";
	}

	@PostMapping("/savePerson")
	public User savePerson(@RequestBody User user) {
		System.out.println("Controller savePerson() method called...");
		return userService.saveMyUserTest(user);
	}

	@GetMapping("/getAllPersons")
	public List<User> getPersons() {
		System.out.println("Controller getPersons() method called...");
		return userService.showAllAcceptedUsers();
	}

	@GetMapping(value = "/producer")
	public String producer(@RequestParam("userName") String userName, @RequestParam("userId") long userId,
			@RequestParam("userDepartment") String userDepartment, @RequestParam("userPasswrord") String userPasswrord,
			@RequestParam("userPhone") String userPhone,@RequestParam("email") String email) {

		User user = new User();
		user.setPassword(userPasswrord);
		user.setUsername(userName);
		user.setId(userId);
		user.setDepartment(userDepartment);
		user.setPhone(userPhone);
		user.setEmail(email);
		
		userService.saveMyUser(user);
		
		User_Roles userRoles = new User_Roles(user.getId(), "USER", "USER", user, user.getUsername(),
				user.getPhone(), 0);
		
		userService.SaveUserFullDetails(userRoles);
		rabbitMQSender.send(user);

		return "MQuser";
	}

	@GetMapping(value = "/producermore")
	public String producer(@RequestParam("exchangeName") String exchange, @RequestParam("routingKey") String routingKey,
			@RequestParam("messageData") String messageData) {

		System.out.println("before producermore");
		amqpTemplate.convertAndSend(exchange, routingKey, messageData);
		System.out.println("after producermore");
		return "MQuser";
	}

	@PostMapping("/LogPerson")
	public String logPerson(@RequestBody User user) {
		System.out.println("Controller logPerson() method called...");
		System.out.println("userService.findByUsernameMethod(user.getUsername()):"
				+ userService.findByUsernameMethod(user.getUsername()));
		if (userService.findByUsernameMethod(user.getUsername()) != null) {

			System.out.println("the user is logged");
			return "welcomepage";

		} else {
			System.out.println("the user is not log");

			return "notfounduser";
		}

	}

	@PostMapping("/LogPersonByRole")
	public String logRole(@RequestBody User user) {
		System.out.println("Controller logRole() method called...");

		if (userService.findByUsernameMethod(user.getUsername()) != null) {

			System.out.println("the user is loged");
			return "welcomepage";

		} else {
			System.out.println("the user is not loged");
			return "notfounduser";
		}

	}

	@RequestMapping("/save-user")
	public String registerUser(@ModelAttribute User user, BindingResult bindingResult, HttpServletRequest request)
			throws MessagingException {
		user.setPassword(WebSecurityConfig.encoder().encode(user.getPassword()));
		System.out.println("The PasswordEncoder is :" + user.getPassword());
		System.out.println("save1");
		HttpSession s = request.getSession();
		User userLog = (User) s.getAttribute("myuser");
		//System.out.println("userLog.getUsername() is" + userLog.getUsername());

		boolean bool = userService.userExisit(user.getUsername());
		System.out.println("the user is here or not ?: *****" + bool);
//		user.setAccepted(1);
//		userService.saveMyUser(user);
//		User_Roles userRoles = new User_Roles(user.getId(), "ADMIN", "ADMIN", user, user.getUsername(),
//				user.getPhone(), 0);
//		userRoles.setId(user.getId());
//		System.out.println("userRoles id is :" + userRoles.getId());
//		userService.SaveUserFullDetails(userRoles);
//		return "welcomepage";

		if (!bool) {
			System.out.println("*******************************");
			User_Roles userRoleDB = userService.findUserRole(userLog.getUsername());
			System.out.println("The userRoleDB is:" + userRoleDB.toString());
			if (userRoleDB.getPermissions().equals("ADMIN")) {
				user.setAccepted(1);
				userService.saveMyUser(user);
				User_Roles userRoles = new User_Roles(user.getId(), "USER", "USER", user, user.getUsername(),
						user.getPhone(), 0);
				userRoles.setId(user.getId());
				System.out.println("userRoles id is :" + userRoles.getId());
				userService.SaveUserFullDetails(userRoles);
			} else {
				userService.saveMyUser(user);
			}

			List<User_Roles> admins = new ArrayList<User_Roles>();
			admins = userService.showAllAdmins();
			// sendMail(admins); //here I send the email
			System.out.println("save2");
			return "display";
		} else {
			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&");
			return "existuser";
		}
	}

	////////////// Email
	@RequestMapping("/send")
	public void sendMail(List<User_Roles> admins) throws MessagingException {

		List<User> useremail = new ArrayList<User>();
		SimpleMailMessage message = new SimpleMailMessage();
		useremail = userService.showAllAcceptedUsers();
		for (int i = 0; i < admins.size(); i++) {
			for (int j = 0; j < useremail.size(); j++) {
				if (useremail.get(j).getUsername().equals(admins.get(i).getUsername())) {
					message.setTo(useremail.get(j).getEmail()); // here we get the admins emails
					message.setSubject("Add Request");
					message.setText("A new add Request, cheak add request page if you want to accept or delete");

					javaMailSender.send(message);
				}
			}
		}

	}

	@GetMapping("/show-request")
	public String showAllRequest(HttpServletRequest request) {
		request.setAttribute("users", userService.showAllNonAcceptedUsers());
		return "AddRequest";
	}

	@RequestMapping("/delete-request")
	public String deleterRequest(@RequestParam int id, HttpServletRequest request) {
		userService.deleteMyUser(id);

		request.setAttribute("users", userService.showAllNonAcceptedUsers());
		return "AddRequest";
	}

	@RequestMapping("/accepte")
	public String requsetaccepted(@RequestParam int id, HttpServletRequest request)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		User user = (User) userService.editUser(id);
		user.setAccepted(1);
		userService.saveMyUser(user);
		User_Roles userRoles = new User_Roles(user.getId(), "USER", "USER", user, user.getUsername(), user.getPhone(),
				0);
		userRoles.setId(user.getId());
		System.out.println("userRoles id is :" + userRoles.getId());
		userService.SaveUserFullDetails(userRoles);
		request.setAttribute("users", userService.showAllNonAcceptedUsers());
		return "AddRequest";
	}

	@GetMapping("/show-users")
	public String showAllUsers(HttpServletRequest request) {
		System.out.println("showAllUsers is called");
		request.setAttribute("users", userService.showAllAcceptedUsers());
		request.setAttribute("usersRoles", userService.showAllUsersRole());
		request.setAttribute("mode", "ALL_USERS");
		return "showallusers";
	}

	@PostMapping("/show-users-test")
	public String showAllUsersTest(@RequestBody String role) {
		System.out.println("showAllUsersTest is called");
		System.out.println("The user role is:" + role);

		if (role.equals("ADMIN")) {
			System.out.println("ADMIN");
			return "showallusers";
		} else {
			System.out.println("USER");
			return "notfounduser";
		}
	}

	@GetMapping("/users-department")
	public String showAllUsersDepartment(HttpServletRequest request) {
		System.out.println("showAllUsersDepartment is called");
		HttpSession s = request.getSession();
		User userdept = (User) s.getAttribute("myuser");
		System.out.println("The session dept is:" + userdept.getDepartment());
		List<User> newuser = userService.showAllAcceptedUsersDept(userdept.getDepartment());
		request.setAttribute("usersDep", newuser);
		request.setAttribute("usersRolesDep", userService.showAllUsersRoleDept(newuser));

		return "UsersDepartment";
	}

	@RequestMapping("/delete-user")
	public String deleteUser(@RequestParam long id, HttpServletRequest request) {

		User user = new User();
		user = userService.editUser(id);
		List<User_Roles> userRole = new ArrayList<User_Roles>();
		userRole = userService.showAllUsersRole();
		for (int i = 0; i < userRole.size(); i++) {
			if (userRole.get(i).getUsername().equals(user.getUsername())) {
				long idRole = userRole.get(i).getId();
				userService.deleteMyUserRole(idRole);
				userService.deleteMyUser(id);
			}
		}
		request.setAttribute("users", userService.showAllAcceptedUsers());
		request.setAttribute("usersroll", userService.showAllUsersRole());
		request.setAttribute("mode", "ALL_USERS");
		return "showallusers";
	}

	@RequestMapping("/delete-user-role")
	public String deleteUserRole(@RequestParam long id, HttpServletRequest request) {

		request.setAttribute("users", userService.showAllAcceptedUsers());
		request.setAttribute("usersroll", userService.showAllUsersRole());
		return "showallusers";
	}

	@RequestMapping("/edit-user")
	public String editUser(@RequestParam long id, HttpServletRequest request) {

		request.setAttribute("user", userService.editUser(id));
		return "updateuser";
	}

	@RequestMapping("/edit-user-role")
	public String editUserRole(@RequestParam long id, HttpServletRequest request) {
		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		request.setAttribute("userrole", userService.editUserRole(id));
		System.out.println("ssssssssssssssssss");
		// request.setAttribute("usersRolesupdate", userService.editUserRole(username));
		return "updateuserrole";
	}

	@RequestMapping("/login")
	public String login(HttpServletRequest request) {

		return "login";
	}

	@RequestMapping("/test")
	public void loginTest(HttpServletRequest request) {
		System.out.println("test is work");

	}

	@RequestMapping("/display")
	public String display(HttpServletRequest request) {

		CurrentUserDetailsService usercurr = new CurrentUserDetailsService(userService);

		System.out.println("I am display controller");
		return "display";
	}

	@RequestMapping("/login-user")
	public String loginUser(@ModelAttribute User user2, HttpServletRequest request, HttpSession session) {
		System.out.println("I am the login user button :3 1");
		System.out.println("the user name is:" + user2.getUsername() + " the password is:" + user2.getPassword());
		if (userService.findByUsernameMethod(user2.getUsername()) != null) {
			System.out.println("the user is loggggggg");
			return "welcomepage";
		} else {
			System.out.println("The login faild");

			return "notfounduser";
		}
	}

	@RequestMapping("/update")
	public String update(@RequestParam int id, @RequestParam int version, @RequestParam String username,
			@RequestParam String firstname, @RequestParam String lastname, @RequestParam int age,
			@RequestParam String password, @RequestParam String department, @RequestParam boolean enabled,
			@RequestParam String email, @RequestParam int accepted, @RequestParam int user_role_id,
			@RequestParam String phone)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("update was called");

		User user = new User(id, username, firstname, lastname, age, password, email, accepted, department, enabled, 0,
				version, phone);

		int res = userService.updating(user);
		if (res == 1) {
			return "welcomepage";
		} else {
			return "stop";
		}

	}

	@RequestMapping("/updaterole")
	public String updaterole(@RequestParam int id, @RequestParam int version, @RequestParam String roles,
			@RequestParam String permissions, @RequestParam String username, @RequestParam String phone)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		System.out.println("updaterole was called");

		User user = new User();
		user = userService.findByUsernameMethod(username);

		User_Roles userrole = new User_Roles(id, roles, permissions, user, username, phone, version);
		int res = userService.updatingRole(userrole);
		if (res == 1) {
			return "welcomepage";
		} else {
			return "stop";
		}

	}

	@RequestMapping(value = "/nohere")
	public String Error403() {
		return "notfounduser";
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView userListReport(HttpServletRequest req, HttpServletResponse res) {

		String typeReport = req.getParameter("type");

		// List of User for test
		List<User> list = new ArrayList<User>();
		list = userService.showAllUsers();

		if (typeReport != null && typeReport.equals("xls")) {

			return new ModelAndView(new ExcelUserListReportView(), "userList", list);
		} else if (typeReport != null && typeReport.equals("pdf")) {

			return new ModelAndView(new PDFUserListReportView(), "userList", list);
		}

		return new ModelAndView("userListReport", "userList", list);
	}

	@RequestMapping(value = "/reportRole", method = RequestMethod.GET)
	public ModelAndView userListReportRole(HttpServletRequest req, HttpServletResponse res) {

		String typeReport = req.getParameter("type");

		// List of User for test
		List<User_Roles> list = new ArrayList<User_Roles>();
		list = userService.showAllUsersRole();

		if (typeReport != null && typeReport.equals("xls")) {

			return new ModelAndView(new ExcelUserListReportRoleView(), "userList", list);
		} else if (typeReport != null && typeReport.equals("pdf")) {

			return new ModelAndView(new PDFUserListReportRoleView(), "userList", list);
		}

		return new ModelAndView("userListReport", "userList", list);
	}

	// not complete :(
	// receives a MultipartFile and saves it in the current location
	@PostMapping("/uploadExcelFile")
	public String uploadFile(Model model, MultipartFile file) throws IOException {
		InputStream in = file.getInputStream();
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
		FileOutputStream f = new FileOutputStream(fileLocation);
		int ch = 0;
		while ((ch = in.read()) != -1) {
			f.write(ch);
		}
		f.flush();
		f.close();
		model.addAttribute("message", "File: " + file.getOriginalFilename() + " has been uploaded successfully!");
		return "excel";
	}

	@RequestMapping(value = "/reportUserDept", method = RequestMethod.GET)
	public ModelAndView userListReportDept(HttpServletRequest req, HttpServletResponse res) {

		String typeReport = req.getParameter("type");

		HttpSession s = req.getSession();
		User user = (User) s.getAttribute("myuser");
		String department = user.getDepartment();
		// List of User for test
		List<User> list = new ArrayList<User>();
		list = userService.showAllAcceptedUsersDept(department);

		if (typeReport != null && typeReport.equals("xls")) {

			return new ModelAndView(new ExcelUserListReportView(), "userList", list);
		} else if (typeReport != null && typeReport.equals("pdf")) {

			return new ModelAndView(new PDFUserListReportView(), "userList", list);
		}

		return new ModelAndView("userListReport", "userList", list);
	}

	@RequestMapping(value = "/reportUserRoleDept", method = RequestMethod.GET)
	public ModelAndView userListReportRoleDept(HttpServletRequest req, HttpServletResponse res) {

		String typeReport = req.getParameter("type");

		HttpSession s = req.getSession();
		User user = (User) s.getAttribute("myuser");
		String department = user.getDepartment();
		// List of User for test
		List<User> list = new ArrayList<User>();
		list = userService.showAllAcceptedUsersDept(department);
		List<User_Roles> list2 = new ArrayList<User_Roles>();
		list2 = userService.showAllUsersRoleDept(list);

		if (typeReport != null && typeReport.equals("xls")) {

			return new ModelAndView(new ExcelUserListReportRoleView(), "userList", list2);
		} else if (typeReport != null && typeReport.equals("pdf")) {

			return new ModelAndView(new PDFUserListReportRoleView(), "userList", list2);
		}

		return new ModelAndView("userListReport", "userList", list2);
	}

	// To get User Login Info
	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody // here it make the return type generate a html page as RestController if I
					// deleted it I must but an html exisist page
	public String currentUserName(Principal principal, HttpServletRequest request, HttpSession session) {

		System.out.println("The user name is:" + principal.getName());
		User user1 = (User) userService.findByUsernameMethod(principal.getName());
		session.setAttribute("myuser", user1);
		return principal.getName();
	}

}