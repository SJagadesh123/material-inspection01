package com.zettamine.mi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.InspectionActual;
import com.zettamine.mi.repository.InspectionActualRepository;

@Service
public class InspectionActualsServiceImpl implements InspectionActualService {

	private InspectionActualRepository inspectionActualRepo;

	public InspectionActualsServiceImpl(InspectionActualRepository inspectionActualRepo) {
		super();
		this.inspectionActualRepo = inspectionActualRepo;
	}

	@Override
	public void save(InspectionActual inspectionActual) {

		inspectionActualRepo.save(inspectionActual);
	}

	@Override
	public InspectionActual getByActualId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InspectionActual> getByInspectionId(Integer id) {

		List<InspectionActual> inspectionActual = inspectionActualRepo.findByInspectionLotId(id);
		
		return inspectionActual;
	}

}
