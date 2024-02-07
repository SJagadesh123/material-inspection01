package com.zettamine.mi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mi.entities.MaterialCharacteristic;

public interface MaterialCharacteristicsRepository extends JpaRepository<MaterialCharacteristic, Serializable> {
	
	
}
