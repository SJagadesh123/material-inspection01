package com.zettamine.mi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zettamine.mi.constants.ViewNames;
import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.service.MaterialService;
import com.zettamine.mi.utility.StringUtils;


@Controller
@RequestMapping("/material-inspection/material")
public class MaterialController {

	private static final Logger LOG = LoggerFactory.getLogger(MaterialController.class);

	private MaterialService materialService;

	public MaterialController(MaterialService materialService) {
		super();
		this.materialService = materialService;
	}

	@GetMapping("/new")
	public String getNewMaterial(Model model) {
		model.addAttribute("material", new Material());
		LOG.info("Returning add material page");
		return ViewNames.ADD_MATERIAL;
	}

	@PostMapping("/add-material")
	public String addMaterial(Material material, RedirectAttributes redirectAttributes, Model model) {

		if (materialService.getByMaterialId(StringUtils
											.removeAllSpaces(material.getMaterialId()
											.toUpperCase()))!=null) {
			model.addAttribute("error", "material with "+ material.getMaterialId()+" already present");
			LOG.info("Material already present with "+material.getMaterialId()+ ",returning add material page");
			return getNewMaterial(model);
		}
		
		if (materialService.isExists(StringUtils
										.trimSpacesBetween(material.getDescription()
										.toLowerCase()))) {
			model.addAttribute("error", "material already present");
			LOG.info("Material already present,returning add material page");
			return getNewMaterial(model);
		}
		
		if (material.getNoOfChar() == 0) {
			try {
				materialService.save(material);
			} catch (Exception e) {
				model.addAttribute("error", "");
				return getNewMaterial(model);
			}
			
			model.addAttribute("msg", "material added succesfully");
			LOG.info("material saved succesfully with 0 characteristics, Returning add material page");
			return getNewMaterial(model);
		}

		

		redirectAttributes.addFlashAttribute("material", material);
		LOG.info("redirected to add Characteristics page");
		return "redirect:/material-inspection/material/addChar";
	}

	@PostMapping("/add-char/add-material")
	public String editMaterialChar(Material material, RedirectAttributes redirectAttributes, Model model) {

		if (material.getNoOfChar() == 0) {
			try {
				materialService.save(material);
			} catch (Exception e) {
				model.addAttribute("error", "");
				return getNewMaterial(model);

			}
			LOG.info("material saved succesfully with 0 characteristics, Returning add material page");
			return getNewMaterial(model);
		}

		redirectAttributes.addFlashAttribute("material", material);
		LOG.info("redirected to add Characteristics page");
		return "redirect:/material-inspection/material/addChar";
	}

	@GetMapping("/addChar")
	public String showCharacteristicForm(Model model) {
		LOG.info("redirecting to add Characteristics page");
		return ViewNames.ADD_CHARACTERISTIC;
	}

	@PostMapping("/add-Characteristic")
	public String addCharacteristic(@ModelAttribute("material") Material material, Model model) {
		
		List<MaterialCharacteristic> list = material.getCharacteristics();

		for (MaterialCharacteristic matCh : list) {
			matCh.setMaterial(material);
		}

		System.out.println(material);

		try {
			materialService.save(material);
		} catch (Exception e) {
			model.addAttribute("error", "duplicate characters are not allowed");
			LOG.info("duplicates found, redirecting to add characteristics form");
			return showCharacteristicForm(model);
			 
		}
		
		model.addAttribute("msg", "characteristics added succesfully");
		LOG.info("Characteristics added successfully, redirected to add material page");
		return getNewMaterial(model);
		
	}

	@GetMapping("/search")
	public String getShowAll(Model model) {

		List<Material> allMaterial = materialService.getAll();

		model.addAttribute("allMaterial", allMaterial);
		LOG.info("Returning to add material page");
		return ViewNames.SHOW_MATERIAL;
	}

	@GetMapping("/show-char/id={id}")
	public String showCharacteristics(@PathVariable("id") String id, Model model) {
		Material material = materialService.getByMaterialId(id);

		List<MaterialCharacteristic> characteristics = material.getCharacteristics();

		model.addAttribute("characteristics", characteristics);
		LOG.info("Returning to show Characteristics page");
		return ViewNames.SHOW_MATERIAL_CHAR;
	}

	@GetMapping("/add-char/id={id}")
	public String editChar(@PathVariable("id") String id, Model model) {
		Material material = materialService.getByMaterialId(id);

		model.addAttribute("materialById", material);
		LOG.info("returning to add Characteristics page");
		return ViewNames.ADD_MATERIAL;
	}
}
