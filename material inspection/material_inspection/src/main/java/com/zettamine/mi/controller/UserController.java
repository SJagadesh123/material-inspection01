package com.zettamine.mi.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.mi.constants.ViewNames;
import com.zettamine.mi.entities.Users;
import com.zettamine.mi.service.UserService;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/material-inspection")
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	 
	@Autowired
	private UserService userService;

	@GetMapping({"/","/logout"})
	public String showUserForm(Model model) {
		
		LOG.info("returning login form");
		
		model.addAttribute("user", new Users());
		
		return ViewNames.LOGIN;
	}

	@PostMapping("/login")
	public String processUser(@ModelAttribute("user") Users user, Model model, HttpSession session) {

		session.removeAttribute("user");
		
		LOG.info("validating user of "+user.getUserId());
		
		
		if (userService.validateUser(user)) {
			Users user1 = userService.getUser(user);
			session.setAttribute("user", user1);
			LOG.info("Login Succesfull returning home page");
			return ViewNames.HOME_PAGE;
		}
		model.addAttribute("message", "Please enter valid credentials");
		LOG.info("Login Failed returning login page");
		return ViewNames.LOGIN;
	}
}
