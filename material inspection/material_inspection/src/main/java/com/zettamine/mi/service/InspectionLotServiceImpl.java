package com.zettamine.mi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.InspectionLot;
import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.repository.InspectionActualRepository;
import com.zettamine.mi.repository.InspectionLotRepository;

@Service
public class InspectionLotServiceImpl implements InspectionLotService {

	private InspectionLotRepository inspectionLotRepo;
	
	private InspectionActualRepository inspectionActualRepo;

	public InspectionLotServiceImpl(InspectionLotRepository inspectionLotRepo,
			InspectionActualRepository inspectionActualRepo) {
		super();
		this.inspectionLotRepo = inspectionLotRepo;
		this.inspectionActualRepo = inspectionActualRepo;
	}

	@Override
	public void save(InspectionLot inspectionLot) {
		
		inspectionLotRepo.save(inspectionLot);
	}

	@Override
	public List<InspectionLot> getAll() {
		
		return inspectionLotRepo.findAll();
	}

	@Override
	public InspectionLot getByLotId(Integer id) {
		Optional<InspectionLot> optLot = inspectionLotRepo.findById(id);
		
		if(optLot.isPresent())
		{
			return optLot.get();
		}
		return null;
	}

	@Override
	public List<InspectionLot> getInspectionLotForProccess() {
		
		List<InspectionLot> listOfLot = inspectionLotRepo.getLotForProcess();
		
		List<InspectionLot> inspectionLot = new ArrayList<InspectionLot>();
		
		
		
		for(InspectionLot lot : listOfLot)
		{
			int charSize = lot.getMaterial().getCharacteristics().size();
			
			if(inspectionActualRepo.findCountByInspectionLot(lot.getInspectionLotId()) < charSize)
			{
				inspectionLot.add(lot);
			}
		}
		
		return inspectionLot;
	}

	@Override
	public List<MaterialCharacteristic> getNonRecordedCharId(Integer id)
	{
		
		InspectionLot inspectionLot = getByLotId(id);
		Material material = inspectionLot.getMaterial();
		List<MaterialCharacteristic> characteristics = material.getCharacteristics();
		
		
		
	 List<Integer> inspCharId = inspectionLot.getInspectionActual().stream().map(insp -> insp.getMaterialCharacteristic().getChId()).collect(Collectors.toList());
		
		List<MaterialCharacteristic> nonRepeatingChars = new ArrayList<>();
		
		for(MaterialCharacteristic chars : characteristics)
		{
			if(!inspCharId.contains(chars.getChId()))
			{
				nonRepeatingChars.add(chars);
			}
		}
		
		return nonRepeatingChars;
		
	}
}