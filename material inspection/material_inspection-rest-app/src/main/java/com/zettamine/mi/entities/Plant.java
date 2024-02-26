package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "* required")
	private String plantId;
	@NotBlank(message = "* required")
	private String plantName;
	@NotBlank(message = "* required")
	private String city;
	@NotBlank(message = "* required")
	private String state;
	private String status = "active";
	
	@JsonIgnore
	@OneToMany(mappedBy = "plant",cascade = CascadeType.ALL)
    private List<InspectionLot> inspectionLots = new ArrayList<>();

}
