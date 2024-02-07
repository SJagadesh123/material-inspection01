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
public class Users {
	@Id
	private Integer userId;
	private String userName;
	private String password;

	@OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<InspectionLot> inspectionLots = new ArrayList<>();

}
