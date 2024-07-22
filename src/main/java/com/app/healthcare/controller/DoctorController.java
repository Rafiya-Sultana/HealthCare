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

import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.LabReportDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.service.DoctorService;
import com.app.healthcare.service.PatientService;

@Controller
@RequestMapping(path = "/doctor")
public class DoctorController
{
	@Autowired
	public DoctorService doctorService;
	
	@Autowired
	public PatientService patientService;
	
	@PostMapping(path = "/add")
    public String addNewDoctor(@Valid DoctorDto doctorDto, BindingResult result, Model model) 
	{
		return doctorService.addNewDoctor(doctorDto, result, model);
    }
	
	@PostMapping(path = "/login")
    public String loginPatient(@Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		return doctorService.loginDoctor(doctorDto, result, model, session);
    }
	
	@GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session)
	{
        return doctorService.viewProfile(model, session);
    }
	
	@GetMapping("/searchAppointment")
    public String searchAppointment(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return doctorService.searchAppointment(searchAppointmentDto, result, model, session);
    }
	
	@PostMapping("/advancedAppointmentSearch")
    public String advancedAppointmentSearch(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return doctorService.advancedAppointmentSearch(searchAppointmentDto, result, model, session);
    }
	
	@GetMapping("/viewReport/{id}")
	public String viewReport(@PathVariable("id") long id, @Valid LabReportDto labReportDto, Model model) 
	{
		return patientService.viewDReport(id, labReportDto, model);
	}
	
	@GetMapping("/download/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename, HttpSession session) 
	{
	    Resource file = patientService.load(filename);

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
	        .body(file);
	}
	
	@GetMapping("/addPrescription/{id}")
	public String addPrescription(@PathVariable("id") long id, @Valid PrescriptionDto prescriptionDto, Model model, HttpSession session) 
	{
		return doctorService.addPrescription(id, prescriptionDto, model, session);
	}
	
	@PostMapping(path = "/savePrescription")
    public String savePrescription(@Valid PrescriptionDto prescriptionDto, SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session) 
	{
		return doctorService.savePrescription(prescriptionDto, searchAppointmentDto, result, model, session);
    }
	
	@GetMapping("/viewPrescription/{id}")
	public String viewPrescription(@PathVariable("id") long id, @Valid PrescriptionDto prescriptionDto, Model model) 
	{
		return doctorService.viewPrescription(id, prescriptionDto, model);
	}
}
