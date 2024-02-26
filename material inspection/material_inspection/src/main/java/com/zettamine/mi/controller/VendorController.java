package com.zettamine.mi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.mi.constants.ViewNames;
import com.zettamine.mi.entities.Vendor;
import com.zettamine.mi.service.VendorService;
import com.zettamine.mi.utility.StringUtils;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/material-inspection/vendor")
public class VendorController {

	private static final Logger LOG = LoggerFactory.getLogger(VendorController.class);
	
	private VendorService vendorService;

	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}

	@GetMapping("/new")
	public String getNewVendor(Model model) {
		model.addAttribute("vendor", new Vendor());
		LOG.info("Returning add vendor page");
		return ViewNames.ADD_VENDOR;
	}

	@PostMapping("/add-vendor")
	public String addVendor(@Valid Vendor vendor, BindingResult errors, Model model) {

		if (errors.hasErrors()) {
			LOG.info("validation failed,returning add vendor page");
			return ViewNames.ADD_VENDOR;
		}

		if (vendorService.existByName(StringUtils.trimSpacesBetween(vendor.getVendorName().toLowerCase()))) {
			model.addAttribute("error", "vendor already present");
			LOG.info("Vendor already present,returning add vendor page");
			return getNewVendor(model);
		}

		vendorService.save(vendor);
		
		model.addAttribute("msg", "vendor saved successfully");
		LOG.info("Vendor saved succesfully, Returning add vendor page");
		return getNewVendor(model);
	}

	@PostMapping("/edit/add-vendor")
	public String updateVendor(@Valid Vendor vendor, BindingResult errors, Model model) {

		if (errors.hasErrors()) {
			LOG.info("validation failed,returning add vendor page");
			return ViewNames.ADD_VENDOR;
		}

		vendorService.save(vendor);
		model.addAttribute("msg", "vendor updated successfully");
		LOG.info("Vendor updated succesfully, Returning add vendor page");
		return getNewVendor(model);
	}

	@GetMapping("/search")
	public String getShowAll(Model model) {

		List<Vendor> allVendors = vendorService.getAll();

		model.addAttribute("allVendors", allVendors);
		LOG.info("Returning all vendors in search page");
		return ViewNames.SHOW_VENDOR;
	}

	@GetMapping("/edit/id={id}")
	public String editVendor(@PathVariable("id") int id, Model model) {
		Vendor vendorById = vendorService.getById(id);

		model.addAttribute("vendorById", vendorById);
		LOG.info("Returning to add vendor page to edit vendor");
		return ViewNames.ADD_VENDOR;
	}

	@GetMapping("/delete/id={id}")
	public String deleteVendor(@PathVariable("id") int id, Model model) {
		Vendor vendorById = vendorService.getById(id);

		vendorById.setStatus("inactive");

		vendorService.save(vendorById);
		LOG.info("Vendor deleted succesfully, Returning search vendor page");
		return "redirect:/material-inspection/vendor/search";
	}

}
