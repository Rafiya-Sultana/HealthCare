package com.app.healthcare.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "labreport_details")
public class LabReportEntity
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Lab_Id")
	private Long labId;

	@Column(name = "Appointment_Id")
	private Long appointmentId;
	
	@Column(name = "File_Name")
	private String fileName;
	
	@Column(name = "File_Extension")
	private String fileExtension;
	
	@Column(name = "Remarks")
	private String remarks;
	
	@Column(name = "Status")
	private Boolean status;

	@Column(name = "Recorded_Date")
	private LocalDateTime RecordedDate;

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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