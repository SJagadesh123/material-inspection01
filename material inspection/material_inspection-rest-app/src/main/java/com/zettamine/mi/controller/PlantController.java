package com.zettamine.mi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.mi.entities.Plant;
import com.zettamine.mi.service.PlantService;
import com.zettamine.mi.utility.StringUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/material-inspection")
public class PlantController {

	private static final Logger LOG = LoggerFactory.getLogger(PlantController.class);

	private PlantService plantService;

	public PlantController(PlantService plantService) {
		super();
		this.plantService = plantService;
	}

	@PostMapping("/plant")
	public ResponseEntity<?> addPlant(@Valid @RequestBody Plant plant) {

		String plantId = StringUtils.removeAllSpaces(plant.getPlantId());
		String plantName = StringUtils.removeAllSpaces(plant.getPlantName());
		if (plantService.getByPlantId(plantId).isPresent()) {
			LOG.info("Plant already exists with ID: {}", plantId);
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Plant already exists with ID " + plantId);
		}

		if (plantService.existByPlantname(plantName)) {
			LOG.info("Plant already exists with name: {}", plantName);
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Plant already exists with ID " + plantId);
		}

		plantService.save(plant);
		LOG.info("Plant saved successfully: {}", plant);
		return ResponseEntity.ok("Plant saved successfully");
	}

	@PutMapping("/plant")
	public ResponseEntity<?> updatePlant(@Valid Plant plant) {

		if (plantService.getByPlantId(plant.getPlantId()).isEmpty()) {
			LOG.info("No plant found with id: {}", plant.getPlantId());
			return ResponseEntity.badRequest().body("No plant present with id : " + plant.getPlantId());
		}

		plantService.save(plant);
		LOG.info("Plant with id {} updated successfully", plant.getPlantId());
		return ResponseEntity.ok("Plant updated successfully");
	}

	@GetMapping("/plant")
	public ResponseEntity<List<Plant>> getAllPlants() {
		List<Plant> allPlants = plantService.getAll();
		LOG.info("Retrieved {} plants", allPlants.size());
		return ResponseEntity.ok(allPlants);
	}

	@GetMapping("/plant/{id}")
	public ResponseEntity<?> getPlantById(@PathVariable("id") String id) {
		
		String plantId = StringUtils.removeAllSpaces(id.toUpperCase());
		
		Optional<Plant> plantOpt = plantService.getByPlantId(plantId);
		if (plantOpt.isEmpty()) {
			LOG.info("Plant not found with ID: {}", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No plant found with ID: " + id);
		} else {
			LOG.info("Retrieved plant with ID: {}", id);
			return ResponseEntity.ok(plantOpt.get());
		}
	}

	@DeleteMapping("/plant/{id}")
	public ResponseEntity<?> deletePlant(@PathVariable("id") String id) {

		Optional<Plant> plantOpt = plantService.getByPlantId(id);

		if (plantOpt.isEmpty()) {
			LOG.info("Plant not found with id {} ", id);
			return ResponseEntity.badRequest().body("No plant present with id : " + id);
		} else {
			Plant plant = plantOpt.get();
			plant.setStatus("inactive");
			plantService.save(plant);
			LOG.info("Plant with id {} deleted successfully", id);
			return ResponseEntity.ok("Plant with id " + id + " deleted succesfully");
		}
	}

}
