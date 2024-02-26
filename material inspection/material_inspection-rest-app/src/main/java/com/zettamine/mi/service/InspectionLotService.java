package com.zettamine.mi.service;

import java.util.List;

import com.zettamine.mi.entities.InspectionLot;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.model.SearchCriteria;

public interface InspectionLotService {

	void save(InspectionLot inspectionLot);

	List<InspectionLot> getAll();

	InspectionLot getByLotId(Integer id);
	
	List<InspectionLot> getInspectionLotForProccess();
	
	List<MaterialCharacteristic> getNonRecordedCharId(Integer id);
	
	List<InspectionLot> getCompleted();

	List<InspectionLot> getPendingLot();

	List<InspectionLot> getBySearchCriteria(SearchCriteria criteria);
}