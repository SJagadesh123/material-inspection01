package com.zettamine.mi.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.mi.constants.ViewNames;
import com.zettamine.mi.entities.InspectionActual;
import com.zettamine.mi.entities.InspectionLot;
import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.entities.Plant;
import com.zettamine.mi.entities.Users;
import com.zettamine.mi.model.SearchCriteria;
import com.zettamine.mi.service.InspectionActualService;
import com.zettamine.mi.service.InspectionLotService;
import com.zettamine.mi.service.MaterialService;
import com.zettamine.mi.service.PlantService;
import com.zettamine.mi.service.VendorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/material-inspection")
public class InspectionLotController {

	private static final Logger LOG = LoggerFactory.getLogger(InspectionLotController.class);

	private VendorService vendorService;
	private MaterialService materialService;
	private PlantService plantService;
	private InspectionLotService inspectionLotService;
	private InspectionActualService inspectionActualService;

	public InspectionLotController(VendorService vendorService, MaterialService materialService,
			PlantService plantService, InspectionLotService inspectionLotService,
			InspectionActualService inspectionActualService) {
		super();
		this.vendorService = vendorService;
		this.materialService = materialService;
		this.plantService = plantService;
		this.inspectionLotService = inspectionLotService;
		this.inspectionActualService = inspectionActualService;
	}

	@PostMapping("/inspection")
	public ResponseEntity<String> addInspection(@RequestBody InspectionLot inspection) {
		LOG.info("Received Inspection Lot: {}", inspection);

		Date createdDate = inspection.getInspCreatedDate();
		Date startDate = inspection.getInspStartDate();
		LocalDate now = LocalDate.now();
		Date currentDate = Date.valueOf(now);

		if (inspection.getMaterial().getCharacteristics().isEmpty()) {
			LOG.warn("Inspection material has no characteristics");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Material has no characteristics");
		}

		if (createdDate.compareTo(currentDate) > 0 || startDate.compareTo(currentDate) > 0) {
			LOG.warn("Date must be less than current date");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date must be less than current date");
		}

		if (createdDate.compareTo(startDate) > 0) {
			LOG.warn("Start date must be greater than created date");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Start date must be greater than created date");
		}

		inspectionLotService.save(inspection);
		LOG.info("Inspection Lot added successfully: {}", inspection);

		return ResponseEntity.status(HttpStatus.OK).body("Inspection Lot added successfully");
	}

	@GetMapping("/process-isp")
	public String processInspection(Model model) {
		List<InspectionLot> inspectionLotForProccess = inspectionLotService.getInspectionLotForProccess();

		model.addAttribute("inspection", inspectionLotForProccess);

		return ViewNames.PROCESS_ISP;
	}

	@GetMapping("/add-actuals{id}")
	public String getAddActuals(@PathVariable("id") Integer id, Model model) {

		InspectionLot inspectionLot = inspectionLotService.getByLotId(id);
		Material material = inspectionLot.getMaterial();
		List<MaterialCharacteristic> characteristics = inspectionLotService.getNonRecordedCharId(id);

		model.addAttribute("characteristics", characteristics);
		model.addAttribute("material", material);
		model.addAttribute("inspectionLot", inspectionLot);
		model.addAttribute("actual", new InspectionActual());

		return ViewNames.ADD_ACTUALS;
	}

	@PostMapping("/add-actuals")
	public String addActuals(InspectionActual inspectionActual) {

		inspectionActualService.save(inspectionActual);

		return "redirect:process-isp";
	}

	@GetMapping("/view-actuals{id}")
	public String viewActuals(@PathVariable("id") Integer id, Model model) {
		List<InspectionActual> inspActuals = inspectionActualService.getByInspectionId(id);

		String uom = inspActuals.get(0).getMaterialCharacteristic().getUom();

		model.addAttribute("inspActuals", inspActuals);
		model.addAttribute("uom", uom);

		return ViewNames.SHOW_ACTUALS;
	}

	@GetMapping("/results")
	public String getCompletedLot(Model model) {
		List<InspectionLot> completedLot = inspectionLotService.getCompleted();

		model.addAttribute("inspection", completedLot);

		return ViewNames.SHOW_RESULTS;
	}

	@GetMapping("/pending")
	public String getApprovalPending(Model model) {
		List<InspectionLot> pendingLot = inspectionLotService.getPendingLot();

		model.addAttribute("inspection", pendingLot);

		return ViewNames.APPROVAL_PENDING;
	}

	@GetMapping("/start-approval{id}")
	public String getStartApproval(@PathVariable Integer id, Model model) {

		InspectionLot inspectionLot = inspectionLotService.getByLotId(id);

		List<MaterialCharacteristic> characteristics = inspectionLot.getMaterial().getCharacteristics();

		List<InspectionActual> inspectionActual = inspectionLot.getInspectionActual();

		model.addAttribute("inspectionLot", inspectionLot);
		model.addAttribute("characteristics", characteristics);
		model.addAttribute("inspectionActual", inspectionActual);

		return ViewNames.START_APPROVAL;
	}

	@PostMapping("/add-approval")
	public String addApproval(InspectionLot inspection, HttpSession session) {

		inspection.setInspEndDate(Date.valueOf(LocalDate.now()));

		Users user = (Users) session.getAttribute("user");
		inspection.setUser(user);

		inspectionLotService.save(inspection);

		return "redirect:pending";

	}

	@GetMapping("/show-details/id={id}")
	public String getCompleteDetails(@PathVariable Integer id, Model model) {

		InspectionLot inspectionLot = inspectionLotService.getByLotId(id);

		List<MaterialCharacteristic> characteristics = inspectionLot.getMaterial().getCharacteristics();

		List<InspectionActual> inspectionActual = inspectionLot.getInspectionActual();

		model.addAttribute("inspection", inspectionLot);
		model.addAttribute("characteristics", characteristics);
		model.addAttribute("inspectionActual", inspectionActual);

		return ViewNames.VIEW_DETAILS;
	}

	@GetMapping("/search")
	public String getSearchPage(Model model) {

		model.addAttribute("searchCriteria", new SearchCriteria());
		model.addAttribute("inspection", new InspectionLot());
		model.addAttribute("material", new Material());
		model.addAttribute("plant", new Plant());

		return ViewNames.SEARCH_PAGE;
	}

	@PostMapping("/search-lot")
	public String searchForm(SearchCriteria criteria, Model model) {
		LocalDate startDate = criteria.getStartDate().toLocalDate();
		LocalDate endDate = criteria.getEndDate().toLocalDate();

		long diff = ChronoUnit.DAYS.between(startDate, endDate);

		if (diff > 90) {
			model.addAttribute("error", "the date range should be 90 days or less");
			return getSearchPage(model);
		}

		criteria.setMaterialId(criteria.getMaterialId().isBlank() ? null : criteria.getMaterialId());
		criteria.setPlantId(criteria.getPlantId().isBlank() ? null : criteria.getPlantId());
		criteria.setStatus(criteria.getStatus().isBlank() ? null : criteria.getStatus());

		List<InspectionLot> inspectionLot = inspectionLotService.getBySearchCriteria(criteria);

		System.err.println(inspectionLot);

		model.addAttribute("inspection", inspectionLot);

		return ViewNames.SEARCH_RESULT;

	}

}
