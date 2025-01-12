package com.example.controller;

import com.example.dao.*;

import com.example.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/user")
public class UserViewController {

	@Autowired
	private UserDAO userDAO; // Injecting UserDAO

	@Autowired
	private ActivityDAO programDAO;

	@Autowired
	private SchoolDAO schoolDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Show registration form
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		List<School> schools = schoolDAO.getAllSchools();
		model.addAttribute("schools", schools);
		model.addAttribute("client", new UserViewModel());
		return "user/register";
	}

	@PostMapping("/register")
	public String processRegisterForm(@ModelAttribute("client") UserViewModel client, Model model) {
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

		// Save the user with encoded password
		client.setPassword(passwordEncoder.encode(client.getPassword()));
		client.setRole(3); // Default role as student
		try {
			userDAO.saveUser(client);
		} catch (Exception e) {
			model.addAttribute("error", "An unexpected error occurred. Please try again.");
			e.printStackTrace();
			return "user/register";
		}

		return "redirect:/user/login?registered=true";
	}

	// Show login form
	@GetMapping("/login")
	public String showLoginForm() {
		return "user/login";
	}

	@GetMapping("/dashboard")
	public String showDashboard(Model model, Authentication authentication) {
		try {
			// Add debug logging
			System.out.println("Authentication object: " + authentication);
			System.out.println("Authentication name: " + authentication.getName());
			System.out.println("Authentication authorities: " + authentication.getAuthorities());

			UserViewModel user = userDAO.findUserByEmail(authentication.getName());
			if (user == null) {
				System.out.println("User not found in database");
				return "redirect:/user/login";
			}

			System.out.println("Found user: " + user.getEmail() + " with role: " + user.getRole());

			// Load all required data
			List<ActivityViewModel> programs = programDAO.getAllActivities();
			List<School> schools = schoolDAO.getAllSchools();
			List<Resource> resources = resourceDAO.getAllResources();

			// Add to model
			model.addAttribute("client", user);
			model.addAttribute("programs", programs);
			model.addAttribute("schools", schools);
			model.addAttribute("resources", resources);

			return "user/dashboard";
		} catch (Exception e) {
			System.out.println("Error in dashboard: " + e.getMessage());
			e.printStackTrace();
			return "redirect:/user/login?error=true";
		}
	}

	// Show profile
	@GetMapping("/profile")
	public String showProfile(Model model, Authentication authentication) {
		UserViewModel user = userDAO.findUserByEmail(authentication.getName());
		model.addAttribute("client", user);
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
	public String updateProfile(@ModelAttribute("client") UserViewModel updatedClient, HttpSession session,
			Model model) {
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
		userDAO.updateUser(loggedInClient); // Ensure your DAO has an updateUser method

		// Update client in the session
		session.setAttribute("loggedInClient", loggedInClient);

		// Redirect without model data; the profile page will fetch data from session
		return "redirect:/user/profile?message=Profile+updated+successfully";
	}

	// Show user list
	@GetMapping("/userList")
	public String showUserList(Model model, Authentication authentication) {
		UserViewModel user = userDAO.findUserByEmail(authentication.getName());
		List<UserViewModel> userList = userDAO.findUsersBySchool(user.getSchool());
		model.addAttribute("userList", userList);
		model.addAttribute("schoolName", user.getSchool());
		return "user/userList";
	}

	@PreAuthorize("hasRole('TEACHER')")
	@GetMapping("/createUser")
	public String showCreateUser(Model model, Authentication authentication) {
		UserViewModel teacher = userDAO.findUserByEmail(authentication.getName());
		UserViewModel newUser = new UserViewModel();
		newUser.setSchool(teacher.getSchool());
		model.addAttribute("client", newUser);
		return "user/createUser";
	}

	@PostMapping("/createUser")
	public String processCreateUser(@ModelAttribute("client") UserViewModel client, Model model,
			Authentication authentication) {
		UserViewModel teacher = userDAO.findUserByEmail(authentication.getName());

		// Validate school matches teacher's school
		if (!client.getSchool().equals(teacher.getSchool())) {
			model.addAttribute("error", "The user's school must match your school.");
			return "user/createUser";
		}

		// Check if email exists
		if (userDAO.findUserByEmail(client.getEmail()) != null) {
			model.addAttribute("error", "Email already exists.");
			return "user/createUser";
		}

		// Save new user
		client.setPassword(passwordEncoder.encode(client.getPassword()));
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
	public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserViewModel updatedUser,
			HttpSession session, Model model) {
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
	public String logout() {
		// Spring Security handles the actual logout
		return "redirect:/user/login";
	}

}
