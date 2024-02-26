package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;

import com.zettamine.mi.entities.Plant;

public interface PlantService {

	void save(Plant plant);
	
	List<Plant> getAll();

	Optional<Plant> getByPlantId(String id);
	
	boolean existByPlantname(String name);
}
