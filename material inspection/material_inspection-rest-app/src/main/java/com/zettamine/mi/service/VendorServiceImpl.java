package com.zettamine.mi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.Vendor;
import com.zettamine.mi.repository.VendorRepository;
import com.zettamine.mi.utility.StringUtils;

@Service
public class VendorServiceImpl implements VendorService {

	private VendorRepository vendorRepo;

	public VendorServiceImpl(VendorRepository vendorRepo) {
		super();
		this.vendorRepo = vendorRepo;
	}

	@Override
	public void save(Vendor vendor) {
		Vendor newVendor = new Vendor(vendor.getVendorId(),
				StringUtils.trimSpacesBetween(vendor.getVendorName().toLowerCase()),
				StringUtils.trimSpacesBetween(vendor.getEmail().toLowerCase()), vendor.getState().toLowerCase(),
				StringUtils.trimSpacesBetween(vendor.getCity().toLowerCase()), vendor.getStatus().toLowerCase(), null);

		vendorRepo.save(newVendor);
	}

	@Override
	public List<Vendor> getAll() {

		return vendorRepo.findAllByStatus("active");
	}

	@Override
	public Optional<Vendor> getById(int id) {
		
		return vendorRepo.findById(id);
	}

	@Override
	public boolean existByName(String vendorName) {
		// TODO Auto-generated method stub
		return vendorRepo.existsByVendorName(vendorName);
	}

}
