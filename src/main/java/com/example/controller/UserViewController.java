package com.example.controller;

import com.example.dao.*;

import com.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserViewController {

	@Autowired
	private UserDAO userDAO;  // Injecting UserDAO

	@Autowired
	private ActivityDAO programDAO;

	@Autowired
	private SchoolDAO schoolDAO;

	@Autowired
	private ResourceDAO resourceDAO;


	// Show registration form
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<School> schools = schoolDAO.getAllSchools(); 
	    model.addAttribute("schools", schools);
		model.addAttribute("client", new UserViewModel());
		return "user/register";
	}

	@PostMapping("/register")
	public String processRegisterForm(@ModelAttribute("client") UserViewModel client, Model model, HttpSession session) {
		// Validation checks
		if (client.getFullName() == null || client.getFullName().isEmpty()) {
			model.addAttribute("error", "Full Name is required.");
			return "user/register";
		}
		if (client.getEmail() == null || client.getEmail().isEmpty()) {
			model.addAttribute("error", "Email is required.");
			return "user/register";
		}
		if (client.getPassword() == null || client.getPassword().length() < 6) {
			model.addAttribute("error", "Password must be at least 6 characters.");
			return "user/register";
		}
		
		if (client.getIdentityCardNumber() == null || client.getIdentityCardNumber().isEmpty()) {
			model.addAttribute("error", "Identity Card Number is required.");
			return "user/register";
		}
		if (!client.getPassword().equals(client.getCheckPassword())) {
			model.addAttribute("error", "Password and Confirm Password must match.");
			return "user/register";
		}

		// Check if email exists
		UserViewModel existingUser = userDAO.findUserByEmail(client.getEmail());
		if (existingUser != null) {
			model.addAttribute("error", "Email already exists.");
			return "user/register";
		}
		// Check if Identity Card Number exists
	    List<UserViewModel> usersWithSameIdCard = userDAO.findUsersByIC(client.getIdentityCardNumber());
	    if (!usersWithSameIdCard.isEmpty()) {
	        model.addAttribute("error", "An account with this Identity Card Number already exists. Please use a different one.");
	        return "user/register";
	    }

	    // Save the user
	    client.setRole(3); // Default role as student
	    System.out.println("Calling saveUser in UserDAO...");
	    try {
	        userDAO.saveUser(client);
	    } catch (Exception e) {
	        if (e.getMessage().contains("ConstraintViolationException") || e.getMessage().contains("Duplicate entry")) {
	            model.addAttribute("error", "An account with the same email or Identity Card Number already exists.");
	            return "user/register";
	        }
	        model.addAttribute("error", "An unexpected error occurred. Please try again.");
	        e.printStackTrace();
	        return "user/register";
	    }

		// Set session attribute
		session.setAttribute("loggedInClient", client);

		// Redirect to the dashboard
		return "redirect:/user/dashboard";
	}

	// Show login form
	@GetMapping("/login")
	public String showLoginForm() {
		return "user/login";
	}

	// Process the login form
	@PostMapping("/login")
	public String processLoginForm(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model) {
		UserViewModel user = userDAO.findUserByEmail(email);
		if (user == null || !user.getPassword().equals(password)) {
			model.addAttribute("error", "Invalid credentials.");
			return "user/login";
		}
		session.setAttribute("loggedInClient", user);
		return "redirect:/user/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String showDashboard(HttpSession session, Model model) {
	    UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
	    if (loggedInClient == null) {
	        return "redirect:/user/login";
	    }

	    // Fetch all programs, schools, and resources
	    List<ActivityViewModel> programs = programDAO.getAllActivities();
	    List<School> schools = schoolDAO.getAllSchools();
	    List<Resource> resources = resourceDAO.getAllResources();

	    // Log the data to check if it is fetched correctly
	    System.out.println("Programs: " + programs);
	    System.out.println("Schools: " + schools);
	    System.out.println("Resources: " + resources);

	    // Add data to the model
	    model.addAttribute("client", loggedInClient);
	    model.addAttribute("programs", programs);
	    model.addAttribute("schools", schools);
	    model.addAttribute("resources", resources);

	    return "user/dashboard";
	}


	// Show profile
	@GetMapping("/profile")
	public String showProfile(HttpSession session, Model model) {
		UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
		if (loggedInClient == null) {
			model.addAttribute("error", "You must log in to view the profile.");
			return "redirect:/user/login";
		}

		// Ensure updated data is added to the model
		model.addAttribute("client", loggedInClient);
		return "user/profile";
	}

	@GetMapping("/updateProfile")
	public String showUpdateProfile(HttpSession session, Model model) {
	    UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");
	    if (loggedInClient == null) {
	        model.addAttribute("error", "You must log in to view the profile.");
	        return "redirect:/user/login";
	    }

	    List<School> schoolList = schoolDAO.getAllSchools(); // Retrieve school list from DB
	    model.addAttribute("client", loggedInClient);
	    model.addAttribute("schools", schoolList); // Pass school list to the view
	    return "user/updateProfile";
	}


	@PostMapping("/updateProfile")
	public String updateProfile(@ModelAttribute("client") UserViewModel updatedClient, HttpSession session, Model model) {
		UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

		if (loggedInClient == null) {
			model.addAttribute("error", "You must log in to update your profile.");
			return "redirect:/user/login";
		}

		// Update the loggedInClient details
		loggedInClient.setFullName(updatedClient.getFullName());
		loggedInClient.setEmail(updatedClient.getEmail());
		loggedInClient.setSchool(updatedClient.getSchool());
		loggedInClient.setDateOfBirth(updatedClient.getDateOfBirth());
		loggedInClient.setIdentityCardNumber(updatedClient.getIdentityCardNumber());

		String newPassword = updatedClient.getPassword();
		if (newPassword != null && !newPassword.isEmpty()) {
			loggedInClient.setPassword(newPassword);
		}

		// Save updated client details to the database
		userDAO.updateUser(loggedInClient);  // Ensure your DAO has an updateUser method

		// Update client in the session
		session.setAttribute("loggedInClient", loggedInClient);

		// Redirect without model data; the profile page will fetch data from session
		return "redirect:/user/profile?message=Profile+updated+successfully";
	}

	// Show user list
	@GetMapping("/userList")
	public String showUserList(Model model, HttpSession session) {
		UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

		if (loggedInClient == null) {
			model.addAttribute("error", "You must log in to view the user list.");
			return "redirect:/user/login";
		}

		String schoolName = loggedInClient.getSchool(); // Get the school name from the session
		System.out.println("School Name from session: " + schoolName); // Debug log

		List<UserViewModel> userList = userDAO.findUsersBySchool(schoolName); // Retrieve users for that school
		System.out.println("Users found: " + userList.size()); // Debug log

		model.addAttribute("userList", userList);
		model.addAttribute("schoolName", schoolName);

		return "user/userList";
	}

	@GetMapping("/createUser")
	public String showCreateUser(Model model, HttpSession session) {
	    UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

	    if (loggedInClient == null) {
	        model.addAttribute("error", "You must log in to create a user.");
	        return "redirect:/user/login";
	    }

	    // Check if the logged-in user has permission
	    if (loggedInClient.getRole() != 2) {  // Assuming role 2 is for admin
	        model.addAttribute("error", "You do not have permission to create a user.");
	        return "redirect:/user/dashboard";
	    }

	    // Create a new user with the same school as the logged-in client
	    UserViewModel newUser = new UserViewModel();
	    newUser.setSchool(loggedInClient.getSchool()); // Pre-fill the school
	    model.addAttribute("client", newUser);

	    return "user/createUser";
	}
	
	@PostMapping("/createUser")
	public String processCreateUser(@ModelAttribute("client") UserViewModel client, Model model, HttpSession session) {
	    UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

	    if (loggedInClient == null) {
	        model.addAttribute("error", "You must log in to create a user.");
	        return "redirect:/user/login";
	    }

	    if (loggedInClient.getRole() != 2) {
	        model.addAttribute("error", "You do not have permission to create a user.");
	        return "redirect:/user/dashboard";
	    }
	    // Check if email already exists
	    UserViewModel existingUserByEmail = userDAO.findUserByEmail(client.getEmail());
	    if (existingUserByEmail != null) {
	        model.addAttribute("error", "The email is already in use. Please use a different email.");
	        return "user/createUser";
	    }

		// Check if Identity Card Number exists
	    List<UserViewModel> usersWithSameIdCard = userDAO.findUsersByIC(client.getIdentityCardNumber());
	    if (!usersWithSameIdCard.isEmpty()) {
	        model.addAttribute("error", "An account with this Identity Card Number already exists. Please use a different one.");
	        return "user/createUser";
	    }

	    // Validate that the school matches the logged-in client's school
	    if (!client.getSchool().equals(loggedInClient.getSchool())) {
	        model.addAttribute("error", "The user's school must match your school.");
	        return "user/createUser";
	    }

	    // Save the user
	    client.setRole(3); // Default role as student
	    userDAO.saveUser(client);

	    return "redirect:/user/userList";
	}


	// Show edit user form
	@GetMapping("/editUser/{id}")
	public String showEditUser(@PathVariable("id") Long id, Model model, HttpSession session) {
		UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

		if (loggedInClient == null) {
			model.addAttribute("error", "You must log in to edit a user.");
			return "redirect:/user/login";
		}

		// Fetch the user by id
		UserViewModel user = userDAO.findUserById(id);
		if (user != null) {
			model.addAttribute("user", user);
		} else {
			model.addAttribute("error", "User not found.");
			return "redirect:/user/userList";
		}

		return "user/editUser"; // JSP page for displaying the user edit form
	}
	@PostMapping("/editUser/{id}")
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserViewModel updatedUser, HttpSession session, Model model) {
	    UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

	    if (loggedInClient == null) {
	        model.addAttribute("error", "You must log in to update a user.");
	        return "redirect:/user/login";
	    }

	    // Ensure the updated school matches the logged-in user's school
	    updatedUser.setSchool(loggedInClient.getSchool());

	    // Save the user
	    userDAO.updateUser(updatedUser);

	    return "redirect:/user/userList"; // Redirect back to the user list page after update
	}


	// Delete user by ID
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Long id, HttpSession session, Model model) {
		UserViewModel loggedInClient = (UserViewModel) session.getAttribute("loggedInClient");

		if (loggedInClient == null) {
			model.addAttribute("error", "You must log in to delete a user.");
			return "redirect:/user/login";
		}

		// Check if the user exists
		UserViewModel user = userDAO.findUserById(id);
		if (user == null) {
			model.addAttribute("error", "User not found.");
			return "redirect:/user/userList";
		}

		// Delete the user
		userDAO.deleteUserById(id);

		// Redirect to user list with success message
		model.addAttribute("success", "User deleted successfully.");
		return "redirect:/user/userList";
	}



	// Logout function
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// Invalidate the session to log out the user
		session.invalidate();

		// Redirect to the login page after logout
		return "redirect:/user/login";
	}


}
