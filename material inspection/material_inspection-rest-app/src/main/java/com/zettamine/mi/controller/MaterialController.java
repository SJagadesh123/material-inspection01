package com.zettamine.mi.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.service.MaterialService;
import com.zettamine.mi.utility.StringUtils;

@RestController
@RequestMapping("/material-inspection")
public class MaterialController {

	private static final Logger LOG = LoggerFactory.getLogger(MaterialController.class);

	private MaterialService materialService;

	public MaterialController(MaterialService materialService) {
		super();
		this.materialService = materialService;
	}

	@PostMapping("/material")
	public ResponseEntity<String> addMaterial(@RequestBody Material material) {

		if (materialService
				.getByMaterialId(StringUtils.removeAllSpaces(material.getMaterialId().toUpperCase())) != null) {
			LOG.info("Material already present with " + material.getMaterialId() + ", returning add material page");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Material with ID " + material.getMaterialId() + " already exists");
		}

		if (materialService.isExists(StringUtils.trimSpacesBetween(material.getDescription().toLowerCase()))) {
			LOG.info("Material already present, returning add material page");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Material already exists");
		}

		LOG.info("Material added succesfully");
		return ResponseEntity.status(HttpStatus.OK).body("Redirected to add Characteristics page");
	}

	@PostMapping("/characteristic/{id}")
	public ResponseEntity<?> addCharacteristic(@RequestBody List<MaterialCharacteristic> characteristics,
			@PathVariable("id") String id) {

		Optional<Material> materialOpt = materialService.getByMaterialId(id);

		if (materialOpt.isEmpty()) {
			LOG.info("Material not present with id: " + id);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Material not present with id: " + id);
		} else {
			Material material = materialOpt.get();
			for (MaterialCharacteristic matCh : characteristics) {
				matCh.setMaterial(material);
			}
			
			material.setCharacteristics(characteristics);
			try {
				materialService.save(material);
				LOG.info("Characteristics added successfully for material with id: " + id);
				return ResponseEntity.status(HttpStatus.OK).body("Characteristics added successfully");
			} catch (Exception e) {
				LOG.error("Error occurred while adding characteristics for material with id: " + id, e);
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate characteristics are not allowed");
			}
		}
	}

	@GetMapping("/material")
	public ResponseEntity<List<Material>> getAllMaterials() {
		List<Material> allMaterial = materialService.getAll();
		LOG.info("Retrieved all materials successfully");
		return ResponseEntity.status(HttpStatus.OK).body(allMaterial);
	}

	@GetMapping("/characteristic/{id}")
	public ResponseEntity<?> showCharacteristics(@PathVariable("id") String id) {
		Optional<Material> materialOpt = materialService.getByMaterialId(id);

		if (materialOpt.isEmpty()) {
			LOG.info("Material not present with id: " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material not present with id: " + id);
		} else {
			Material material = materialOpt.get();

			List<MaterialCharacteristic> characteristics = material.getCharacteristics();
			LOG.info("Characteristics retrieved successfully for material with id: " + id);
			return ResponseEntity.status(HttpStatus.OK).body(characteristics);
		}
	}

	
}
