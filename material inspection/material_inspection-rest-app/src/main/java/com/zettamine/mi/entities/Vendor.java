package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "inspectionLots")
public class Vendor {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vendor_sequence")
    @SequenceGenerator(name = "vendor_sequence", sequenceName = "vendor_sequence", allocationSize = 1, initialValue = 5001)
	private Integer vendorId;
	@NotBlank(message = "* required")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "*provide valid name")
	private String vendorName;
	@NotBlank(message = "* required")
	private String email;
	@NotBlank(message = "* required")
	private String state;
	@NotBlank(message = "* required")
	private String city;
	private String status = "active";
	
	@JsonIgnore
	@OneToMany(mappedBy = "vendor",cascade = CascadeType.ALL)
    private List<InspectionLot> inspectionLots = new ArrayList<>();


}
