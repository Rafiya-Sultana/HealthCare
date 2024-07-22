package com.app.healthcare.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.HospitalDoctorDto;
import com.app.healthcare.dto.HospitalDto;
import com.app.healthcare.dto.SearchAppointmentDto;
import com.app.healthcare.service.HospitalService;

@Controller
@RequestMapping(path = "/hospital")
public class HospitalController
{
	@Autowired
	public HospitalService hospitalService;
	
	@PostMapping(path = "/add")
    public String addNewHospital(@Valid HospitalDto hospitalDto, BindingResult result, Model model) 
	{
		return hospitalService.addNewHospital(hospitalDto, result, model);
    }
	
	@PostMapping(path = "/login")
    public String loginHospital(@Valid HospitalDto hospitalDto, BindingResult result, Model model, HttpSession session) 
	{
		return hospitalService.loginHospital(hospitalDto, result, model, session);
    }
	
	@GetMapping("/profile")
    public String viewProfile(Model model, HttpSession session)
	{
        return hospitalService.viewProfile(model, session);
    }
	
	@GetMapping("/mapDoctor")
    public String mapDoctor(@Valid HospitalDoctorDto hospitalDoctorDto, Model model, HttpSession session)
	{
        return hospitalService.mapDoctor(hospitalDoctorDto, model, session);
    }
	
	@PostMapping(path = "/addDoctor")
    public String addDoctor(@Valid HospitalDoctorDto hospitalDoctorDto, BindingResult result, Model model, HttpSession session) 
	{
		return hospitalService.addDoctor(hospitalDoctorDto, result, model, session);
    }
	
	@GetMapping("/viewDoctor")
    public String viewDoctor(Model model, HttpSession session)
	{
        return hospitalService.viewDoctor(model, session);
    }
	
	@GetMapping("/searchDoctor")
    public String searchDoctor(@Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session)
	{
        return hospitalService.searchDoctor(doctorDto, result, model, session);
    }
	
	@PostMapping("/advancedsearch")
    public String advancedsearch(@Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session)
	{
        return hospitalService.advancedsearch(doctorDto, result, model, session);
    }
	
	@GetMapping("/advAdd/{id}")
	public String advancedAdd(@PathVariable("id") long id, @Valid AppointmentDto appointmentDto, Model model) 
	{
		return hospitalService.advancedAdd(id, model);
	}
	
	@PostMapping(path = "/bookAppoinment")
    public String bookAppoinment(@Valid AppointmentDto appointmentDto, @Valid DoctorDto doctorDto, BindingResult result, Model model, HttpSession session) 
	{
		return hospitalService.bookAppoinment(appointmentDto, doctorDto, result, model, session);
    }
	
	@GetMapping("/searchAppointment")
    public String searchAppointment(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return hospitalService.searchAppointment(searchAppointmentDto, result, model, session);
    }
	
	@PostMapping("/advancedAppointmentSearch")
    public String advancedAppointmentSearch(@Valid SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session)
	{
        return hospitalService.advancedAppointmentSearch(searchAppointmentDto, result, model, session);
    }
}
