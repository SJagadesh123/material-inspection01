package com.zettamine.mi.service;

import java.util.List;

import com.zettamine.mi.entities.InspectionActual;

public interface InspectionActualService {

	void save(InspectionActual inspectionActual);
	
	InspectionActual getByActualId(Integer id);
	
	List<InspectionActual> getByInspectionId(Integer id);
}
