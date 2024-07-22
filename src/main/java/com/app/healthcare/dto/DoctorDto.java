package com.app.healthcare.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.app.healthcare.model.AppointmentEntity;

public class DoctorDto
{
	private Long doctorId;
	private String doctorName;
	private Long doctorPhoneno;
	private String doctorEmailId;
	private String doctorDegree;
	private String doctorDesigination;
	private String doctorAddress;
	private String doctorUserName;
	private String doctorPassword;
	private LocalDateTime doctorRecordedDate;
	private List<AppointmentEntity> appointments;
	private String visitingHours;
	
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Long getDoctorPhoneno() {
		return doctorPhoneno;
	}
	public void setDoctorPhoneno(Long doctorPhoneno) {
		this.doctorPhoneno = doctorPhoneno;
	}
	public String getDoctorEmailId() {
		return doctorEmailId;
	}
	public void setDoctorEmailId(String doctorEmailId) {
		this.doctorEmailId = doctorEmailId;
	}
	public String getDoctorDegree() {
		return doctorDegree;
	}
	public void setDoctorDegree(String doctorDegree) {
		this.doctorDegree = doctorDegree;
	}
	public String getDoctorDesigination() {
		return doctorDesigination;
	}
	public void setDoctorDesigination(String doctorDesigination) {
		this.doctorDesigination = doctorDesigination;
	}
	public String getDoctorAddress() {
		return doctorAddress;
	}
	public void setDoctorAddress(String doctorAddress) {
		this.doctorAddress = doctorAddress;
	}
	public String getDoctorUserName() {
		return doctorUserName;
	}
	public void setDoctorUserName(String doctorUserName) {
		this.doctorUserName = doctorUserName;
	}
	public String getDoctorPassword() {
		return doctorPassword;
	}
	public void setDoctorPassword(String doctorPassword) {
		this.doctorPassword = doctorPassword;
	}
	public LocalDateTime getDoctorRecordedDate() {
		return doctorRecordedDate;
	}
	public void setDoctorRecordedDate(LocalDateTime doctorRecordedDate) {
		this.doctorRecordedDate = doctorRecordedDate;
	}
	public List<AppointmentEntity> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<AppointmentEntity> appointments) {
		this.appointments = appointments;
	}
	public String getVisitingHours() {
		return visitingHours;
	}
	public void setVisitingHours(String visitingHours) {
		this.visitingHours = visitingHours;
	}
}