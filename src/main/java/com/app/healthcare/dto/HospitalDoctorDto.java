package com.app.healthcare.dto;

import java.time.LocalDateTime;

public class HospitalDoctorDto
{
	private Long hdId;
	private Long hospitalId;
	private Long doctorId;
	private String visitingHours;
	private Boolean status;
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