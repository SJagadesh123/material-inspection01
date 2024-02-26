package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;

import com.zettamine.mi.entities.Vendor;

public interface VendorService {

	void save(Vendor vendor);
	
	List<Vendor> getAll();
	
	Optional<Vendor> getById(int id);
	
	boolean existByName(String vendorName);
}
