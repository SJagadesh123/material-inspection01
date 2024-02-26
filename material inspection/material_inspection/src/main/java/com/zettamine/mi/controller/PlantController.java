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
import com.zettamine.mi.entities.Plant;
import com.zettamine.mi.service.PlantService;
import com.zettamine.mi.utility.StringUtils;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/material-inspection/plant")
public class PlantController {

	private static final Logger LOG = LoggerFactory.getLogger(PlantController.class);
	
	private PlantService plantService;

	public PlantController(PlantService plantService) {
		super();
		this.plantService = plantService;
	}

	@GetMapping("/new")
	public String getNewPlant(Model model) {
		model.addAttribute("plant", new Plant());
		LOG.info("Returning add plant page");
		return ViewNames.ADD_PLANT;
	}

	@PostMapping("/add-plant")
	public String addPlant(@Valid Plant plant, BindingResult errors, Model model) {
		if (errors.hasErrors()) {
			LOG.info("validation failed,returning add plant page");
			return ViewNames.ADD_PLANT;
		}

		if (plantService.getByPlantId(StringUtils.removeAllSpaces(plant.getPlantId())) != null) {
			model.addAttribute("error", "plant exist with id " + plant.getPlantId());
			LOG.info("Plant already present,returning add plant page");
			return getNewPlant(model);
		}

		plantService.save(plant);

		model.addAttribute("msg", "plant saved successfully");
		LOG.info("Plant saved succesfully, Returning add plant page");
		return getNewPlant(model);
	}

	@PostMapping("/edit/add-plant")
	public String updatePlant(@Valid Plant plant, BindingResult errors, Model model) {
		if (errors.hasErrors()) {
			LOG.info("validation failed,returning add plant page");
			return ViewNames.ADD_PLANT;
		}

		plantService.save(plant);

		model.addAttribute("msg", "plant updated successfully");
		LOG.info("Plant updated succesfully, Returning add plant page");
		return getNewPlant(model);
	}

	@GetMapping("/search")
	public String getShowAll(Model model) {

		List<Plant> allPlants = plantService.getAll();

		model.addAttribute("allPlants", allPlants);
		LOG.info("Returning all plants in search page");
		return ViewNames.SHOW_PLANT;
	}

	@GetMapping("/edit/id={id}")
	public String editPlant(@PathVariable("id") String id, Model model) {

		Plant plantById = plantService.getByPlantId(id);

		model.addAttribute("plantById", plantById);
		LOG.info("Returning to add plant page to edit plant");
		return ViewNames.ADD_PLANT;
	}

	@GetMapping("/delete/id={id}")
	public String deletePlant(@PathVariable("id") String id, Model model) {
		Plant plantById = plantService.getByPlantId(id);

		plantById.setStatus("inactive");

		plantService.save(plantById);
		LOG.info("Plant deleted succesfully, Returning search plant page");
		return "redirect:/material-inspection/plant/search";
	}

}
