package com.zettamine.mi.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "insp_act")
@Data
@ToString(exclude = {"inspectionLot","materialCharacteristic"})
public class InspectionActual {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer actualId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "inspection_lot_id")
    private InspectionLot inspectionLot;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ch_Id")
	private MaterialCharacteristic materialCharacteristic;
	
	private Double maxMeasurement;
	
	private Double minMeasurement;
	
	
}

