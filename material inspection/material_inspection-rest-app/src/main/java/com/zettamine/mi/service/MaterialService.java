package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;


import com.zettamine.mi.entities.Material;

public interface MaterialService {

	void save(Material material) throws Exception;

	List<Material> getAll();

	Optional<Material> getByMaterialId(String id);

	boolean isExists(String materialName);
}
