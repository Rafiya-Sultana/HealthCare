package com.app.healthcare.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "prescription_details")
public class PrescriptionEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Prescription_Id")
	private Long prescriptionId;

	@Column(name = "Appointment_Id")
	private Long appointmentId;
	
	@Column(name = "Prescription")
	private String prescription;
	
	@Column(name = "Lab_Test")
	private String labTest;
	
	@Column(name = "Status")
	private Boolean status;

	@Column(name = "Recorded_Date")
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDateTime getRecordedDate() {
		return RecordedDate;
	}

	public void setRecordedDate(LocalDateTime recordedDate) {
		RecordedDate = recordedDate;
	}
}