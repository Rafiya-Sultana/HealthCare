package com.app.healthcare.service;

import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import com.app.healthcare.dto.AppointmentDto;
import com.app.healthcare.dto.DoctorDto;
import com.app.healthcare.dto.LabReportDto;
import com.app.healthcare.dto.PatientDto;
import com.app.healthcare.dto.PrescriptionDto;
import com.app.healthcare.dto.SearchAppointmentDto;

public interface PatientService 
{
	public void init();
	
	public String addNewPatient(PatientDto patientDto, BindingResult result, Model model);

	public String loginPatient(PatientDto patientDto, BindingResult result, Model model, HttpSession session);

	public String viewProfile(Model model, HttpSession session);

	public String searchDoctor(PatientDto patientDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String advancedsearch(DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String advancedAdd(long id, Model model);

	public String bookAppoinment(AppointmentDto appointmentDto, DoctorDto doctorDto, BindingResult result, Model model, HttpSession session);

	public String searchAppointment(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String advancedAppointmentSearch(SearchAppointmentDto searchAppointmentDto, BindingResult result, Model model, HttpSession session);

	public String addReport(long id, LabReportDto labReportDto, Model model);

	public String uploadFile(LabReportDto labReportDto, Model model, HttpSession session, MultipartFile file);

	public String viewReport(long id, LabReportDto labReportDto, Model model);

	public Resource load(String filename);

	public String viewDReport(long id, LabReportDto labReportDto, Model model);

	public String viewPrescription(long id, PrescriptionDto prescriptionDto, Model model);
}
