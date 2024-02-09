package com.zettamine.mi.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zettamine.mi.entities.InspectionActual;

public interface InspectionActualRepository extends JpaRepository<InspectionActual, Serializable> {

	@Query(value = "select count(*) from insp_act where inspection_lot_id = :id", nativeQuery = true)
	Integer findCountByInspectionLot(Integer id);
	
	@Query(value = "select * from insp_act where inspection_lot_id = :id" , nativeQuery = true)
	List<InspectionActual> findByInspectionLotId(Integer id);
	
	@Query(value = "select  a.inspection_lot_id  from insp_act  a inner join material_characteristic  b on a.ch_id= b.ch_id \r\n"
			+ "where a.max_measurement <= b.tol_ul and a.min_measurement  >=    b.tol_ll",nativeQuery = true)
	Set<Integer> findPassedInspectionLotId();
	
}
