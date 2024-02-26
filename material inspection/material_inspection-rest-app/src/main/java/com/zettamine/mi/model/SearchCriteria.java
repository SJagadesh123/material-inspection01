package com.zettamine.mi.model;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class SearchCriteria {
	
	private String materialId;
	
	private String plantId;
	
	private Integer vendorId;
	
	private Date startDate;
	
	
	private Date endDate;
	
	private String status;
}
