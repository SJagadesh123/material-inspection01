package com.zettamine.mi.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"material","inspectionActual"})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"material_id", "ch_Desc"}))
public class MaterialCharacteristic {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mat_chr_sequence")
    @SequenceGenerator(name = "mat_chr_sequence", sequenceName = "mat_chr_sequence", allocationSize = 1, initialValue = 101)
	private Integer chId;
	
	@NotBlank(message = "* required")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "*provide valid name")
	private String chDesc;
	
	@NotBlank(message = "* required")
	@Pattern(regexp = "^[a-zA-Z\\s]*$", message = "*provide valid unit of measurement")
	private String uom;

	@Column(name = "tol_ul")
	@NotNull
	@Min(value = 0, message = "Upper limit must be greater than 0")
	private Double upperLimit;

	@Column(name = "tol_ll")
	@NotNull
	@Min(value = 0, message = "Lower limit must be greater than 0")
	private Double lowerLimit;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "material_id")
	private Material material;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "materialCharacteristic", cascade = CascadeType.ALL)
	List<InspectionActual> inspectionActual = new ArrayList<>();
}
