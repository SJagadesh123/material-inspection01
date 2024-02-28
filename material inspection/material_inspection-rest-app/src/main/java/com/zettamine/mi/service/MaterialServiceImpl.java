package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.repository.MaterialRepository;
import com.zettamine.mi.utility.StringUtils;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	private MaterialRepository materialRepo;

	public MaterialServiceImpl(MaterialRepository materialRepo) {
		super();
		this.materialRepo = materialRepo;
	}

	@Override
	public void save(Material material) throws Exception {
		
		material.setMaterialId(StringUtils.trimSpacesBetween(material.getMaterialId()).toUpperCase());
		material.setDescription(StringUtils.trimSpacesBetween(material.getDescription()).toLowerCase());
		material.setMaterialType(StringUtils.trimSpacesBetween(material.getMaterialType()).toLowerCase());
		
		List<MaterialCharacteristic> characteristics = material.getCharacteristics();
		
		for(MaterialCharacteristic chars : characteristics)
		{
			chars.setChDesc(StringUtils.trimSpacesBetween(chars.getChDesc()).toLowerCase());
			chars.setUom(StringUtils.trimSpacesBetween(chars.getUom()).toLowerCase());
		}

		materialRepo.save(material);
		
	}

	@Override
	public List<Material> getAll() {
		
		return materialRepo.findAll();
	}

	@Override
	public Optional<Material> getByMaterialId(String id) {

		Optional<Material> optional = materialRepo.findById(id);
		
		
		return optional;
	}

	@Override
	public boolean isExists(String materialName) {
		
		return materialRepo.existsByDescription(materialName);
	}

}
