package com.zettamine.mi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.mi.constants.ViewNames;
import com.zettamine.mi.entities.Users;
import com.zettamine.mi.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/material-inspection")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;


	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		LOG.info("user logged out, returning login form");
		return ResponseEntity.ok("You have been logged out");
	}

	@PostMapping("/login")
	public ResponseEntity<?> processUser(@RequestBody Users user, HttpSession session) {

		session.removeAttribute("user");

		LOG.info("Validating user with ID '{}'", user.getUserId());

		if (userService.validateUser(user)) {
			Users user1 = userService.getUser(user);
			session.setAttribute("user", user1);
			LOG.info("Login successful for user with ID '{}'", user.getUserId());
			return ResponseEntity.ok("Login successful");
		}

		LOG.info("Login failed for user with username '{}'", user.getUserName());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	}

}
