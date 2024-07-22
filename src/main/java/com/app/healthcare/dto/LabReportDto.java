package com.app.healthcare.dto;

import java.time.LocalDateTime;

public class LabReportDto
{
	private Long labId;
	private Long appointmentId;
	private String fileName;
	private String fileExtension;
	private String remarks;
	private Boolean status;
	private LocalDateTime RecordedDate;
	private String fileFullName;
	
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
	public String getFileFullName() {
		return fileFullName;
	}
	public void setFileFullName(String fileFullName) {
		this.fileFullName = fileFullName;
	}
}