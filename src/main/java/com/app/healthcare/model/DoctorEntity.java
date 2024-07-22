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
@Table(name = "doctor_details")
public class DoctorEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Doctor_Id")
	private Long doctorId;

	@Column(name = "Name")
	private String doctorName;  
	
	@Column(name = "Phoneno")
	private Long doctorPhoneno;

	@Column(name = "Email_Id")
	private String doctorEmailId; 
	
	@Column(name = "Degree")
	private String doctorDegree;
	
	@Column(name = "Desigination")
	private String doctorDesigination;

	@Column(name = "Address")
	private String doctorAddress;

	@Column(name = "Username")
	private String doctorUserName;
	
	@Column(name = "Password")
	private String doctorPassword;

	@Column(name = "Recorded_Date")
	private LocalDateTime doctorRecordedDate;
	
    @OneToMany(mappedBy = "doctor")
    private List<AppointmentEntity> appointments;

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
}