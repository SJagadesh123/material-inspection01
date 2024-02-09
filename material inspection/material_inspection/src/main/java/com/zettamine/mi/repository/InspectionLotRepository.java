package com.zettamine.mi.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zettamine.mi.entities.InspectionLot;

public interface InspectionLotRepository extends JpaRepository<InspectionLot, Serializable> {

	@Query(value = "select * from isp_lot where insp_end_date is null",nativeQuery = true)
	List<InspectionLot> getLotForProcess();
	
	
	
	
}
