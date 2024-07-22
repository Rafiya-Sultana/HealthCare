package com.app.healthcare.dto;

public class SearchAppointmentDto 
{
	private Long patientPhoneno;
	private String appointmentDate;
	
	public Long getPatientPhoneno() {
		return patientPhoneno;
	}
	public void setPatientPhoneno(Long patientPhoneno) {
		this.patientPhoneno = patientPhoneno;
	}
	public String getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
}
