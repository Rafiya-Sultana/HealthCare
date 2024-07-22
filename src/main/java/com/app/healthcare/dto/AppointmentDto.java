package com.app.healthcare.dto;

import java.time.LocalDateTime;

public class AppointmentDto {
	private Long appointmentId;
	private Long hospitalId;
	private Long doctorId;
	private Long patientId;
	private Boolean status;
	private LocalDateTime appointmentDate;
	private LocalDateTime recordedDate;
	private String hospitalName;
	private String doctorName;
	private String patientName;
	private Long patientPhoneno;
	private String searchDate;
	private String onlineURL;
	
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public Long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public Long getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public LocalDateTime getRecordedDate() {
		return recordedDate;
	}
	public void setRecordedDate(LocalDateTime recordedDate) {
		this.recordedDate = recordedDate;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Long getPatientPhoneno() {
		return patientPhoneno;
	}
	public void setPatientPhoneno(Long patientPhoneno) {
		this.patientPhoneno = patientPhoneno;
	}
	public String getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}
	public String getOnlineURL() {
		return onlineURL;
	}
	public void setOnlineURL(String onlineURL) {
		this.onlineURL = onlineURL;
	}
}