package com.zettamine.mi.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "isp_lot")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@ToString(exclude = "inspectionActual")
public class InspectionLot {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "isp_lot_sequence")
    @SequenceGenerator(name = "isp_lot_sequence", sequenceName = "isp_lot_sequence", allocationSize = 1, initialValue = 4801)
	private Integer inspectionLotId;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "material_id")
	private Material material;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "vendor_id")
	private Vendor vendor;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "plant_id")
	private Plant plant;
	private Date inspCreatedDate;
	private Date inspStartDate;
	private Date inspEndDate;
	private String inspectionResult;
	private String inspectionRemarks;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Users user;
	
	@OneToMany(mappedBy = "inspectionLot", cascade = CascadeType.ALL)
	List<InspectionActual> inspectionActual = new ArrayList<>();

}
