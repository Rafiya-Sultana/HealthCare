package com.app.healthcare.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.healthcare.model.AppointmentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository < AppointmentEntity, Long >
{
	@Query("SELECT u FROM AppointmentEntity u WHERE u.hospital.hospitalId =:hId ORDER BY u.appointmentDate DESC")
	public List<AppointmentEntity> findByHospitalId(@Param("hId") Long hId);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.hospital.hospitalId =:hId ORDER BY a.appointmentDate DESC")
	public List<Object[]> findAllByHospitalId(@Param("hId") Long hId);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.patient.patientId =:pId ORDER BY a.appointmentDate DESC")
	public List<Object[]> findAllByPatientId(@Param("pId") Long pId);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.hospital.hospitalId =:hId AND a.appointmentDate BETWEEN :sDate AND :eDate")
	public List<Object[]> findByHospitalIdAndAppointmentDate(@Param("hId") Long hId, @Param("sDate") LocalDateTime sDate, @Param("eDate") LocalDateTime eDate);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.patient.patientId =:pId AND a.appointmentDate BETWEEN :sDate AND :eDate")
	public List<Object[]> findByPatientIdAndAppointmentDate(@Param("pId") Long pId, @Param("sDate") LocalDateTime sDate, @Param("eDate") LocalDateTime eDate);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.hospital.hospitalId =:hId AND a.patient.patientId =:pId ORDER BY a.appointmentDate DESC")
	public List<Object[]> findByHospitalIdAndPatientId(@Param("hId") Long hId, @Param("pId") Long pId);

	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.patient.patientId =:pId AND a.doctor.doctorId =:dId AND a.hospital.hospitalId =:hId AND a.appointmentDate BETWEEN :sDate AND :eDate")
	public Optional<AppointmentEntity> findByIdsAndAppointmentDate(@Param("pId") Long pId, @Param("dId") Long dId, @Param("hId") Long hId, @Param("sDate") LocalDateTime sDate, @Param("eDate") LocalDateTime eDate);
	
	@Query("SELECT u FROM AppointmentEntity u WHERE u.appointmentId =:id")
	public List<AppointmentEntity> findByAppointmentId(@Param("id") Long id);

	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.doctor.doctorId =:dId ORDER BY a.appointmentDate DESC")
	public List<Object[]> findAllByDoctorId(@Param("dId") Long dId);
	
	@Query(value = "SELECT a.appointmentId, a.doctor.doctorName AS doctorName, a.patient.patientName AS patientName, a.patient.patientPhoneno AS patientPhoneno, a.status, a.appointmentDate, a.recordedDate, a.onlineURL "
			+ "FROM AppointmentEntity AS a "
			+ "WHERE a.doctor.doctorId =:dId AND a.appointmentDate BETWEEN :sDate AND :eDate")
	public List<Object[]> findByDoctorIdAndAppointmentDate(@Param("dId") Long dId, @Param("sDate") LocalDateTime sDate, @Param("eDate") LocalDateTime eDate);
}
