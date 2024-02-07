package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"material","inspectionActual"})
public class MaterialCharacteristic {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mat_chr_sequence")
    @SequenceGenerator(name = "mat_chr_sequence", sequenceName = "mat_chr_sequence", allocationSize = 1, initialValue = 101)
	private Integer chId;
	private String chDesc;
	private String uom;

	@Column(name = "tol_ul")
	private double upperLimit;

	@Column(name = "tol_ll")
	private double lowerLimit;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "material_id")
	private Material material;
	
	@OneToMany(mappedBy = "materialCharacteristic", cascade = CascadeType.ALL)
	List<InspectionActual> inspectionActual = new ArrayList<>();
}
