package com.app.healthcare.service;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.HospitalDoctorDto;
import com.app.healthcare.dto.HospitalDto;
import com.app.healthcare.dto.SearchAppointmentDto;

public interface HospitalService 
{
	public String addNewHospital(HospitalDto hospitalDto, BindingResult result, Model model);

	public String loginHospital(HospitalDto hospitalDto, BindingResult result, Model model, HttpSession session);

	public String viewProfile(Model model, HttpSession session);

	public String addDoctor(HospitalDoctorDto hospitalDoctorDto, BindingResult result, Model model, HttpSession session);

	public String mapDoctor(HospitalDoctorDto hospitalDoctorDto, Model model, HttpSession session);

	public String viewDoctor(Model model, HttpSession session);

	public String searchDoctor(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String advancedsearch(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String advancedAdd(long id, Model model);

	public String bookAppoinment(AppointmentDto appointmentDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String advancedAppointmentSearch(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);
}
