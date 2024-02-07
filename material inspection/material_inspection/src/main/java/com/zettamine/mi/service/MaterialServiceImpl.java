package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.Material;
import com.zettamine.mi.repository.MaterialRepository;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	private MaterialRepository materialRepo;

	public MaterialServiceImpl(MaterialRepository materialRepo) {
		super();
		this.materialRepo = materialRepo;
	}

	@Override
	public void save(Material material) {

		materialRepo.save(material);
	}

	@Override
	public List<Material> getAll() {
		
		return materialRepo.findAll();
	}

	@Override
	public Material getByMaterialId(String id) {

		Optional<Material> optional = materialRepo.findById(id);
		if(optional.isPresent())
		{
			return optional.get();
		}
		
		return null;
	}

}
