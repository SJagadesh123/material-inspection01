package com.zettamine.mi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.mi.constants.ViewNames;

@Controller
@RequestMapping("/material-inspection")
public class HomePageController {

	
	@GetMapping("/home-page")
	public String forwardToHome(Model model) {
		return ViewNames.HOME_PAGE;
	}

	@GetMapping("/vendor")
	public String forwardToVendor(Model model) {
		return ViewNames.VENDOR;
	}

	@GetMapping("/plant")
	public String forwardToPlant(Model model) {
		return ViewNames.PLANT;
	}

	@GetMapping("/material")
	public String forwardToVendorMaterial(Model model) {
		return ViewNames.MATERIAL;
	}

	@GetMapping("/inspection")
	public String forwardInspection(Model model) {
		return ViewNames.INSPECTION;
	}

}
