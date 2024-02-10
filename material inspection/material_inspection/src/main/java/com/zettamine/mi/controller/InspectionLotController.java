package com.zettamine.mi.controller;

import java.net.http.HttpRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.mi.entities.InspectionActual;
import com.zettamine.mi.entities.InspectionLot;
import com.zettamine.mi.entities.Material;
import com.zettamine.mi.entities.MaterialCharacteristic;
import com.zettamine.mi.entities.Plant;
import com.zettamine.mi.entities.Users;
import com.zettamine.mi.entities.Vendor;
import com.zettamine.mi.service.InspectionActualService;
import com.zettamine.mi.service.InspectionLotService;
import com.zettamine.mi.service.MaterialService;
import com.zettamine.mi.service.PlantService;
import com.zettamine.mi.service.VendorService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/material-inspection/inspection")
public class InspectionLotController {

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

	@GetMapping("/new")
	public String getNewInspectionLot(Model model) {
		model.addAttribute("inspection", new InspectionLot());
		List<Material> material = materialService.getAll();
		List<Vendor> vendor = vendorService.getAll();
		List<Plant> plant = plantService.getAll();

		model.addAttribute("material", material);
		model.addAttribute("vendor", vendor);
		model.addAttribute("plant", plant);
		return "add-inspection";
	}

	@PostMapping({ "/add-inspection" })
	public String addInspection(InspectionLot inspection, Model model) {
		System.out.println(inspection);
		Date createdDate = inspection.getInspCreatedDate();
		Date startDate = inspection.getInspStartDate();
		LocalDate now = LocalDate.now();
		Date currentDate = Date.valueOf(now);

		if (createdDate.compareTo(currentDate) > 0 || startDate.compareTo(currentDate) > 0) {
			System.out.println("------------------");
			model.addAttribute("error", "* Date must be less than current date");

			return getNewInspectionLot(model);
		}

		if (createdDate.compareTo(startDate) > 0) {
			System.out.println("------------------");
			model.addAttribute("error", "* start date must be greater than created date");

			return getNewInspectionLot(model);
		}

		inspectionLotService.save(inspection);

		return "inspection";
	}

	@GetMapping("/process-isp")
	public String processInspection(Model model) {
		List<InspectionLot> inspectionLotForProccess = inspectionLotService.getInspectionLotForProccess();

		model.addAttribute("inspection", inspectionLotForProccess);
		// model.addAttribute("size", size);

		return "process-isp";
	}

	@GetMapping("/add-actuals{id}")
	public String getAddActuals(@PathVariable("id") Integer id, Model model) {

		InspectionLot inspectionLot = inspectionLotService.getByLotId(id);
		Material material = inspectionLot.getMaterial();
		List<MaterialCharacteristic> characteristics = inspectionLotService.getNonRecordedCharId(id);
		List<InspectionActual> inspActuals = inspectionActualService.getByInspectionId(id);
		System.out.println(inspActuals.size());
		model.addAttribute("characteristics", characteristics);
		model.addAttribute("material", material);
		model.addAttribute("inspectionLot", inspectionLot);
		model.addAttribute("actual", new InspectionActual());

		return "add-actuals";
	}

	@PostMapping("/add-actuals")
	public String addActuals(InspectionActual inspectionActual) {

		System.err.println(inspectionActual);

		inspectionActualService.save(inspectionActual);

		return "redirect:process-isp";
	}

	@GetMapping("/view-actuals{id}")
	public String viewActuals(@PathVariable("id") Integer id, Model model) {
		List<InspectionActual> inspActuals = inspectionActualService.getByInspectionId(id);

		String uom = inspActuals.get(0).getMaterialCharacteristic().getUom();

		model.addAttribute("inspActuals", inspActuals);
		model.addAttribute("uom", uom);

		return "show-actuals";
	}

	@GetMapping("/results")
	public String getCompletedLot(Model model) {
		List<InspectionLot> completedLot = inspectionLotService.getCompleted();

		System.out.println(completedLot);
		
		model.addAttribute("inspection", completedLot);

		return "show-results";
	}
	
	@GetMapping("/pending")
	public String getApprovalPending(Model model)
	{
		List<InspectionLot> pendingLot = inspectionLotService.getPendingLot();
		
		model.addAttribute("inspection", pendingLot);
		
		return "approval-pending";
	}
	
	@GetMapping("/start-approval{id}")
	public String getStartApproval(@PathVariable Integer id,Model model)
	{
		
		InspectionLot inspectionLot = inspectionLotService.getByLotId(id);
		
		List<MaterialCharacteristic> characteristics = inspectionLot.getMaterial().getCharacteristics();
		
		
		List<InspectionActual> inspectionActual = inspectionLot.getInspectionActual();
		
		
		model.addAttribute("inspectionLot", inspectionLot);
		model.addAttribute("characteristics", characteristics);
		model.addAttribute("inspectionActual", inspectionActual);
		
		return "start-approval";
	}
	
	@PostMapping("/add-approval")
	public String addApproval(InspectionLot inspection, HttpSession session) {

		System.err.println(inspection);
		
		inspection.setInspEndDate(Date.valueOf(LocalDate.now()));
		
		Users user = (Users) session.getAttribute("user");
		inspection.setUser(user);

		inspectionLotService.save(inspection);

		return "redirect:pending";
		
	}
	

}
