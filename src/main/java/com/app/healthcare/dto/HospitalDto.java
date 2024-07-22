package com.app.healthcare.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.app.healthcare.model.AppointmentEntity;

public class HospitalDto
{
	private Long hospitalId;
	private String hospitalName;
	private Long hospitalPhoneno;
	private String hospitalEmailId;
	private String hospitalAddress;
	private String hospitalUserName;
	private String hospitalPassword;
	private LocalDateTime hospitalRecordedDate;
	private List<AppointmentEntity> appointments;
	
	public Long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}
	public Long getHospitalPhoneno() {
		return hospitalPhoneno;
	}
	public void setHospitalPhoneno(Long hospitalPhoneno) {
		this.hospitalPhoneno = hospitalPhoneno;
	}
	public String getHospitalEmailId() {
		return hospitalEmailId;
	}
	public void setHospitalEmailId(String hospitalEmailId) {
		this.hospitalEmailId = hospitalEmailId;
	}
	public String getHospitalAddress() {
		return hospitalAddress;
	}
	public void setHospitalAddress(String hospitalAddress) {
		this.hospitalAddress = hospitalAddress;
	}
	public String getHospitalUserName() {
		return hospitalUserName;
	}
	public void setHospitalUserName(String hospitalUserName) {
		this.hospitalUserName = hospitalUserName;
	}
	public String getHospitalPassword() {
		return hospitalPassword;
	}
	public void setHospitalPassword(String hospitalPassword) {
		this.hospitalPassword = hospitalPassword;
	}
	public LocalDateTime getHospitalRecordedDate() {
		return hospitalRecordedDate;
	}
	public void setHospitalRecordedDate(LocalDateTime hospitalRecordedDate) {
		this.hospitalRecordedDate = hospitalRecordedDate;
	}
	public List<AppointmentEntity> getAppointments() {
		return appointments;
	}
	public void setAppointments(List<AppointmentEntity> appointments) {
		this.appointments = appointments;
	}
}