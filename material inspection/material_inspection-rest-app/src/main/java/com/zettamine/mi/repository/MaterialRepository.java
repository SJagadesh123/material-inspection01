package com.zettamine.mi.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zettamine.mi.entities.Material;

public interface MaterialRepository extends JpaRepository<Material, Serializable>{

	boolean existsByDescription(String materialName);

}
