package com.app.healthcare.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.LabReportDto;
import com.app.healthcare.dto.PatientDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.service.PatientService;

@Controller
@RequestMapping(path = "/patient")
public class PatientController
{
	@Autowired
	public PatientService patientService;
	
	@PostMapping(path = "/add")
    public String addNewPatient(@Valid PatientDto patientDto, BindingResult result, Model model) 
	{
		return patientService.addNewPatient(patientDto, result, model);
    }
	
	@PostMapping(path = "/login")
    public String loginPatient(@Valid PatientDto patientDto, BindingResult result, Model model, HttpSession session) 
	{
		return patientService.loginPatient(patientDto, result, model, session);
    }
	
	@GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session)
	{
        return patientService.viewProfile(model, session);
    }
	
	@GetMapping("/searchDoctor")
    public String searchDoctor(@Valid PatientDto patientDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session)
	{
        return patientService.searchDoctor(patientDto, doctorDto, result, model, session);
    }
	
	@PostMapping("/advancedsearch")
    public String advancedsearch(@Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session)
	{
        return patientService.advancedsearch(doctorDto, result, model, session);
    }
	
	@GetMapping("/advAdd/{id}")
	public String advancedAdd(@PathVariable("id") long id, @Valid AppointmentDto appointmentDto, Model model) 
	{
		return patientService.advancedAdd(id, model);
	}
	
	@PostMapping(path = "/bookAppoinment")
    public String bookAppoinment(@Valid AppointmentDto appointmentDto, @Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		return patientService.bookAppoinment(appointmentDto, doctorDto, result, model, session);
    }
	
	@GetMapping("/searchAppointment")
    public String searchAppointment(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return patientService.searchAppointment(searchAppointmentDto, result, model, session);
    }
	
	@PostMapping("/advancedAppointmentSearch")
    public String advancedAppointmentSearch(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return patientService.advancedAppointmentSearch(searchAppointmentDto, result, model, session);
    }
	
	@GetMapping("/addReport/{id}")
	public String addReport(@PathVariable("id") long id, @Valid LabReportDto labReportDto, Model model) 
	{
		return patientService.addReport(id, labReportDto, model);
	}
	
	@PostMapping(path = "/fileUpload")
	public String uploadFile(@Valid LabReportDto labReportDto, Model model, HttpSession session, @RequestParam("file") MultipartFile file) 
	{
	    String message = "";

	    try 
	    {
	    	message = patientService.uploadFile(labReportDto, model, session, file);
	    } 
	    catch (Exception e) 
	    {
	    	e.printStackTrace();
	    	
	    	message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
	    }
	    
	    model.addAttribute("message", message);

	    return "addPUpload";
	}
	
	@GetMapping("/viewReport/{id}")
	public String viewReport(@PathVariable("id") long id, @Valid LabReportDto labReportDto, Model model) 
	{
		return patientService.viewReport(id, labReportDto, model);
	}
	
	@GetMapping("/download/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpSession session) 
	{
	    Resource file = patientService.load(filename);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
	        .body(file);
	}
	
	@GetMapping("/viewPrescription/{id}")
	public String viewPrescription(@PathVariable("id") long id, @Valid PrescriptionDto prescriptionDto, Model model) 
	{
		return patientService.viewPrescription(id, prescriptionDto, model);
	}
}
