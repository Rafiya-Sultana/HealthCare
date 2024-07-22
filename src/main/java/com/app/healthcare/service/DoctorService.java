package com.app.healthcare.service;

import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;

public interface DoctorService 
{
	public String addNewDoctor(DoctorDto doctorDto, BindingResult result, Model model);

	public String loginDoctor(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String viewProfile(Model model, HttpSession session);

	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String advancedAppointmentSearch(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String addPrescription(long id, PrescriptionDto prescriptionDto, Model model, HttpSession session);

	public String savePrescription(PrescriptionDto prescriptionDto, SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String viewPrescription(long id, PrescriptionDto prescriptionDto, Model model);

}
