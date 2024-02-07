package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "inspectionLots")
public class Plant {

	@Id
	private String plantId;
	private String plantName;
	private String city;
	private String state;
	private String status = "active";
	
	@OneToMany(mappedBy = "plant",cascade = CascadeType.ALL)
    private List<InspectionLot> inspectionLots = new ArrayList<>();

}