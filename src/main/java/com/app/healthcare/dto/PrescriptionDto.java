package com.app.healthcare.dto;

import java.time.LocalDateTime;

public class PrescriptionDto
{
	private Long prescriptionId;
	private Long appointmentId;
	private String prescription;
	private String labTest;
	private Boolean status;
	private LocalDateTime RecordedDate;
	
	public Long getPrescriptionId() {
		return prescriptionId;
	}
	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}
	public Long getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public String getLabTest() {
		return labTest;
	}
	public void setLabTest(String labTest) {
		this.labTest = labTest;
	}
	public LocalDateTime getRecordedDate() {
		return RecordedDate;
	}
	public void setRecordedDate(LocalDateTime recordedDate) {
		RecordedDate = recordedDate;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}