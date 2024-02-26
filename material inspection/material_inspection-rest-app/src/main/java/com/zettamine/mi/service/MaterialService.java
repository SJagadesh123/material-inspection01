package com.zettamine.mi.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;

import com.zettamine.mi.entities.Material;

public interface MaterialService {

	void save(Material material) throws Exception;

	List<Material> getAll();

	Optional<Material> getByMaterialId(String id);

	boolean isExists(String materialName);
}
