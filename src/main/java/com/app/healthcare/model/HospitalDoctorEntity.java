package com.app.healthcare.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hospital_doctor_details")
public class HospitalDoctorEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HD_Id")
	private Long hdId;

	@Column(name = "Hospital_Id")
	private Long hospitalId;
	
	@Column(name = "Doctor_Id")
	private Long doctorId;
	
	@Column(name = "Visiting_Hours")
	private String visitingHours;

	@Column(name = "Status")
	private Boolean status;

	@Column(name = "Recorded_Date")
	private LocalDateTime hdRecordedDate;

	public Long getHdId() {
		return hdId;
	}

	public void setHdId(Long hdId) {
		this.hdId = hdId;
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

	public String getVisitingHours() {
		return visitingHours;
	}

	public void setVisitingHours(String visitingHours) {
		this.visitingHours = visitingHours;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public LocalDateTime getHdRecordedDate() {
		return hdRecordedDate;
	}

	public void setHdRecordedDate(LocalDateTime hdRecordedDate) {
		this.hdRecordedDate = hdRecordedDate;
	}
}