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
@Table(name = "hospital_details")
public class HospitalEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Hospital_Id")
	private Long hospitalId;

	@Column(name = "Name")
	private String hospitalName;
	
	@Column(name = "Phoneno")
	private Long hospitalPhoneno;

	@Column(name = "Email_Id")
	private String hospitalEmailId;

	@Column(name = "Address")
	private String hospitalAddress;

	@Column(name = "Username")
	private String hospitalUserName;
	
	@Column(name = "Password")
	private String hospitalPassword;

	@Column(name = "Recorded_Date")
	private LocalDateTime hospitalRecordedDate;
	
	@OneToMany(mappedBy = "hospital")
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