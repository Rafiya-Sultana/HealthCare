package com.app.healthcare.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patient_details")
public class PatientEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Patient_Id")
	private Long patientId;

	@Column(name = "Name")
	private String patientName;
	
	@Column(name = "Phoneno")
	private Long patientPhoneno;

	@Column(name = "Email_Id")
	private String patientEmailId;

	@Column(name = "Address")
	private String patientAddress;

	@Column(name = "Username")
	private String patientUserName;
	
	@Column(name = "Password")
	private String patientPassword;

	@Column(name = "Recorded_Date")
	private LocalDateTime patientRecordedDate;
	
	@OneToMany(mappedBy = "patient")
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