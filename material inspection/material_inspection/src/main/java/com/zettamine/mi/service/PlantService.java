package com.zettamine.mi.service;

import java.util.List;

import com.zettamine.mi.entities.Plant;

public interface PlantService {

	void save(Plant plant);
	
	List<Plant> getAll();

	Plant getByPlantId(String id);
}
