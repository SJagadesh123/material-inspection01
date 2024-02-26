package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "inspectionLots")
public class Material {

	@Id
	@NotBlank(message = "* required")
	private String materialId;
	@NotBlank(message = "* required")
	private String description;
	@NotBlank(message = "* required")
	private String materialType;
	@NotNull
	@Max(value=5,message = "max 5 number")
	@Min(value = 0, message = "min 0 number")
	private Integer noOfChar;
	
	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
	private List<MaterialCharacteristic> characteristics = new ArrayList<>();

	@OneToMany(mappedBy = "material",cascade = CascadeType.ALL)
    private List<InspectionLot> inspectionLots = new ArrayList<>();

}
