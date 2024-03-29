package com.zettamine.mi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.zettamine.mi.entities.InspectionActual;
import com.zettamine.mi.entities.InspectionLot;
import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.entities.Users;
import com.zettamine.mi.model.SearchCriteria;
import com.zettamine.mi.repository.InspectionActualRepository;
import com.zettamine.mi.repository.InspectionLotRepository;
import com.zettamine.mi.utility.StringUtils;

@Service
public class InspectionLotServiceImpl implements InspectionLotService {

	private InspectionLotRepository inspectionLotRepo;

	private InspectionActualRepository inspectionActualRepo;

	public InspectionLotServiceImpl(InspectionLotRepository inspectionLotRepo,
			InspectionActualRepository inspectionActualRepo) {
		super();
		this.inspectionLotRepo = inspectionLotRepo;
		this.inspectionActualRepo = inspectionActualRepo;
	}

	@Override
	public void save(InspectionLot inspectionLot) {

		inspectionLotRepo.save(inspectionLot);
	}

	@Override
	public List<InspectionLot> getAll() {

		return inspectionLotRepo.findAll();
	}

	@Override
	public InspectionLot getByLotId(Integer id) {
		Optional<InspectionLot> optLot = inspectionLotRepo.findById(id);

		if (optLot.isPresent()) {
			return optLot.get();
		}
		return null;
	}

	@Override
	public List<InspectionLot> getInspectionLotForProccess() {

		List<InspectionLot> listOfLot = inspectionLotRepo.getLotForProcess();

		Users user = new Users();

		user.setUserId(1002);
		user.setUserName("System");

		List<InspectionLot> inspectionLot = new ArrayList<InspectionLot>();

		List<Integer> id = inspectionActualRepo.findPassedInspectionLotId();

		Map<Integer, Long> idMap = id.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting()));

		System.out.println(id);

		Date endDate = Date.valueOf(LocalDate.now());

		for (InspectionLot lot : listOfLot) {
			int charSize = lot.getMaterial().getCharacteristics().size();

			if (inspectionActualRepo.findCountByInspectionLot(lot.getInspectionLotId()) < charSize) {
				inspectionLot.add(lot);
			} else if (inspectionActualRepo.findCountByInspectionLot(lot.getInspectionLotId()) == charSize) {

				if (lot.getInspectionResult() == null && id.contains(lot.getInspectionLotId())
						&& charSize == idMap.get(lot.getInspectionLotId())) {

					InspectionLot ispLot = new InspectionLot(lot.getInspectionLotId(), lot.getMaterial(),
							lot.getVendor(), lot.getPlant(), lot.getInspCreatedDate(), lot.getInspStartDate(), endDate,
							"pass", "no remarks", user, null);

					inspectionLotRepo.save(ispLot);
				}

			}
		}

		return inspectionLot;
	}

	@Override
	public List<MaterialCharacteristic> getNonRecordedCharId(Integer id) {

		InspectionLot inspectionLot = getByLotId(id);
		Material material = inspectionLot.getMaterial();
		List<MaterialCharacteristic> characteristics = material.getCharacteristics();

		List<Integer> inspCharId = inspectionLot.getInspectionActual().stream()
				.map(insp -> insp.getMaterialCharacteristic().getChId()).collect(Collectors.toList());

		List<MaterialCharacteristic> nonRepeatingChars = new ArrayList<>();

		for (MaterialCharacteristic chars : characteristics) {
			if (!inspCharId.contains(chars.getChId())) {
				nonRepeatingChars.add(chars);
			}
		}

		return nonRepeatingChars;

	}

	@Override
	public List<InspectionLot> getCompleted() {

		List<InspectionLot> listOfLot = inspectionLotRepo.findAll();

		List<InspectionLot> inspectionLot = new ArrayList<InspectionLot>();

		for (InspectionLot lot : listOfLot) {

			if (lot.getInspectionResult() != null) {
				inspectionLot.add(lot);

			}
		}

		return inspectionLot;
	}

	@Override
	public List<InspectionLot> getPendingLot() {

		List<InspectionLot> listOfLot = inspectionLotRepo.findAll();

		List<InspectionLot> pendingInspectionLot = new ArrayList<InspectionLot>();
		for (InspectionLot lot : listOfLot) {
			int charSize = lot.getMaterial().getCharacteristics().size();
			if (inspectionActualRepo.findCountByInspectionLot(lot.getInspectionLotId()) == charSize) {
				if (lot.getInspectionResult() == null) {
					pendingInspectionLot.add(lot);

				}
			}
		}

		return pendingInspectionLot;
	}

	@Override
	public List<InspectionLot> getBySearchCriteria(SearchCriteria criteria) {

		List<InspectionLot> inspectionLot = inspectionLotRepo.findByInspStartDateBetween(criteria.getStartDate(), criteria.getEndDate());
		
		Stream<InspectionLot> stream = inspectionLot.stream();

		
		if (criteria.getMaterialId() != null) {
			criteria.setMaterialId(StringUtils.removeAllSpaces(criteria.getMaterialId()));
			
			stream = stream.filter(n -> n.getMaterial().getMaterialId().equalsIgnoreCase(criteria.getMaterialId()));
		}
		if (criteria.getVendorId() != null) {
			stream = stream.filter(n -> n.getVendor().getVendorId().equals(criteria.getVendorId()));
		}
		if (criteria.getPlantId() != null) {
			criteria.setPlantId(StringUtils.removeAllSpaces(criteria.getPlantId()));
			stream = stream.filter(n -> n.getPlant().getPlantId().equalsIgnoreCase(criteria.getPlantId()));
		}
		if (criteria.getStatus() != null) {
			criteria.setStatus(StringUtils.removeAllSpaces(criteria.getStatus()));
			stream = stream.filter(n -> n.getInspectionResult()!=null).filter(n -> n.getInspectionResult().equalsIgnoreCase(criteria.getStatus()));
		}
		
		return stream.collect(Collectors.toList());
	}

	
	/*
	 * @Override public List<InspectionLot> getBySearchCriteria(InspectionLot
	 * inspection) {
	 * 
	 * String query = "select * from isp_lot where insp_start_date>=" +
	 * inspection.getInspStartDate() + " and insp_start_date <=" +
	 * inspection.getInspEndDate();
	 * 
	 * if (inspection.getMaterial().getMaterialId() != null ||
	 * !inspection.getMaterial().getMaterialId().isBlank()) { query = query +
	 * " and material_id = '" +
	 * inspection.getMaterial().getMaterialId().toUpperCase() + "'"; }
	 * 
	 * if (inspection.getPlant().getPlantId() != null ||
	 * !inspection.getPlant().getPlantId().isBlank()) { query = query +
	 * " and plant_id = '" + inspection.getPlant().getPlantId().toUpperCase() + "'";
	 * }
	 * 
	 * if (inspection.getVendor().getVendorId() != null) { query = query +
	 * " and vendor_id = " + inspection.getVendor().getVendorId(); }
	 * 
	 * if (inspection.getInspectionResult() != null ||
	 * !inspection.getInspectionResult().isBlank()) { query = query +
	 * " and status = '" + inspection.getInspectionResult().toLowerCase() + "'"; }
	 * 
	 * return inspectionLotRepo.getLotBySearchCriteria(query);
	 * 
	 * 
	 * }
	 */

}
