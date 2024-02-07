package com.zettamine.mi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.Plant;
import com.zettamine.mi.repository.PlantRepository;

@Service
public class PlantServiceImpl implements PlantService {

	private PlantRepository plantRepo;
	
	
	public PlantServiceImpl(PlantRepository plantRepo) {
		super();
		this.plantRepo = plantRepo;
	}

	@Override
	public void save(Plant plant) {
		Plant newPlant = new Plant(plant.getPlantId(), 
								   plant.getPlantName().toLowerCase(), 
								   plant.getCity().toLowerCase(), 
								   plant.getState().toLowerCase(), 
								   plant.getStatus().toLowerCase(),null);
		
		
		plantRepo.save(newPlant);
	}

	@Override
	public List<Plant> getAll() {
		
		return plantRepo.findAllByStatus("active");
	}

	@Override
	public Plant getByPlantId(String id) {
		
		return plantRepo.findById(id).get();
	}

}
