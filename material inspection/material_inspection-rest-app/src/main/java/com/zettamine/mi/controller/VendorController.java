package com.zettamine.mi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.mi.entities.Vendor;
import com.zettamine.mi.service.VendorService;
import com.zettamine.mi.utility.StringUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/material-inspection")
public class VendorController {

	private static final Logger LOG = LoggerFactory.getLogger(VendorController.class);

	private VendorService vendorService;

	public VendorController(VendorService vendorService) {
		super();
		this.vendorService = vendorService;
	}

	@PostMapping("/vendor")
	public ResponseEntity<?> addVendor(@Valid @RequestBody Vendor vendor) {

		String vendorName = StringUtils.trimSpacesBetween(vendor.getVendorName().toLowerCase());
		if (vendorService.existByName(vendorName)) {
			LOG.info("Vendor already present with name: {}", vendorName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Vendor already present");
		}

		vendorService.save(vendor);
		LOG.info("Vendor with name '{}' saved successfully", vendorName);
		return ResponseEntity.ok("Vendor saved successfully");
	}

	@PutMapping("/vendor")
	public ResponseEntity<String> updateVendor(@Valid @RequestBody Vendor vendor) {

		if (vendorService.getById(vendor.getVendorId()) == null) {
			LOG.info("No vendor found with id: {}", vendor.getVendorId());
			return ResponseEntity.badRequest().body("No vendor present with id : " + vendor.getVendorId());
		}

		vendorService.save(vendor);
		LOG.info("Vendor with id {} updated successfully", vendor.getVendorId());
		return ResponseEntity.ok("Vendor updated successfully");
	}

	@GetMapping("/vendor")
	public ResponseEntity<List<Vendor>> getAllVendors() {
		List<Vendor> allVendors = vendorService.getAll();
		LOG.info("Retrieved {} vendors", allVendors.size());
		return ResponseEntity.ok(allVendors);
	}

	@DeleteMapping("/vendor/{id}")
	public ResponseEntity<?> deleteVendor(@PathVariable("id") int id) {

		Optional<Vendor> vendorOpt = vendorService.getById(id);

		if (vendorOpt.isEmpty()) {
			LOG.info("Vendor not found with id {} ", id);
			return ResponseEntity.badRequest().body("No vendor present with id : " + id);
		} else {

			Vendor vendor = vendorOpt.get();
			vendor.setStatus("inactive");
			vendorService.save(vendor);
			LOG.info("Vendor with id {} deleted successfully", id);
			return ResponseEntity.ok("Vendor with id " + id + " deleted succesfully");
		}
	}

}
