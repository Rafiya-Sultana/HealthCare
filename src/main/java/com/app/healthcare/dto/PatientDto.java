package com.app.healthcare.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.app.healthcare.model.AppointmentEntity;

public class PatientDto
{
	private Long patientId;
	private String patientName;
	private Long patientPhoneno;
	private String patientEmailId;
	private String patientAddress;
	private String patientUserName;
	private String patientPassword;
	private LocalDateTime patientRecordedDate;
	private List<AppointmentEntity> appointments;
	
	public Long getPatientId() {
		return patientId;
	}
	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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
	public String getPatientEmailId() {
		return patientEmailId;
	}
	public void setPatientEmailId(String patientEmailId) {
		this.patientEmailId = patientEmailId;
	}
	public String getPatientAddress() {
		return patientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		this.patientAddress = patientAddress;
	}
	public String getPatientUserName() {
		return patientUserName;
	}
	public void setPatientUserName(String patientUserName) {
		this.patientUserName = patientUserName;
	}
	public String getPatientPassword() {
		return patientPassword;
	}
	public void setPatientPassword(String patientPassword) {
		this.patientPassword = patientPassword;
	}
	public LocalDateTime getPatientRecordedDate() {
		return patientRecordedDate;
	}
	public void setPatientRecordedDate(LocalDateTime patientRecordedDate) {
		this.patientRecordedDate = patientRecordedDate;
	}
	public List<AppointmentEntity> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<AppointmentEntity> appointments) {
		this.appointments = appointments;
	}
}