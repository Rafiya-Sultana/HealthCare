package com.app.healthcare.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "appointment_details")
public class AppointmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Appointment_Id")
	private Long appointmentId;

	@ManyToOne
    @JoinColumn(name = "Hospital_Id")
    private HospitalEntity hospital;
	
	@ManyToOne
    @JoinColumn(name = "Doctor_Id")
    private DoctorEntity doctor;

	@ManyToOne
    @JoinColumn(name = "Patient_Id")
    private PatientEntity patient;

	@Column(name = "Status")
	private Boolean status;

	@Column(name = "Appointment_Date")
	private LocalDateTime appointmentDate;
	
	@Column(name = "Online_URL")
	private String onlineURL;

	@Column(name = "Recorded_Date")
	private LocalDateTime recordedDate;

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public HospitalEntity getHospital() {
		return hospital;
	}

	public void setHospital(HospitalEntity hospital) {
		this.hospital = hospital;
	}

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
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

	public String getOnlineURL() {
		return onlineURL;
	}

	public void setOnlineURL(String onlineURL) {
		this.onlineURL = onlineURL;
	}
}