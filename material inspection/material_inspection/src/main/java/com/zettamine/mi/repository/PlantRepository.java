package com.zettamine.mi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mi.entities.Plant;

public interface PlantRepository extends JpaRepository<Plant, String> {

	List<Plant> findAllByStatus(String string);
	
	

}
