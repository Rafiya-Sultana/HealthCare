package com.app.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.healthcare.model.HospitalDoctorEntity;

@Repository
public interface HospitalDoctorRepository extends JpaRepository < HospitalDoctorEntity, Long >
{
	@Query("SELECT h FROM HospitalDoctorEntity h WHERE h.hospitalId =:hId AND h.doctorId =:dId")
	public Optional<HospitalDoctorEntity> findByHospitalAndDoctorId(@Param("hId") Long hId, @Param("dId") Long dId);
	
	@Query("SELECT h.doctorId FROM HospitalDoctorEntity h WHERE h.hospitalId =:hId")
	public List<Long> findByHospitalId(@Param("hId") Long hId);

	@Query("SELECT h.hospitalId FROM HospitalDoctorEntity h WHERE h.doctorId IN (:doctorIds)")
	public List<Long> findByDoctorId(@Param("doctorIds") Long doctorIds);
	
	@Query(value = "SELECT d.Doctor_Id, d.Name, d.Phoneno, d.Email_Id, d.Degree, d.Desigination, d.Address, h.Visiting_Hours FROM hospital_doctor_details h LEFT JOIN doctor_details AS d ON d.Doctor_Id = h.Doctor_Id WHERE h.Doctor_Id IN (:doctorIds)", nativeQuery = true)
	public List<Object[]> findHospitalDoctors(@Param("doctorIds") List<Long> doctorIds);
	
	@Query(value = "SELECT d.Doctor_Id, d.Name, d.Phoneno, d.Email_Id, d.Degree, d.Desigination, d.Address, h.Visiting_Hours FROM hospital_doctor_details h LEFT JOIN doctor_details AS d ON d.Doctor_Id = h.Doctor_Id WHERE h.Doctor_Id IN (:doctorIds) AND d.Desigination LIKE %:designation% ORDER BY d.Name ASC", nativeQuery = true)
	public List<Object[]> findHospitalDoctorsByDesignation(@Param("doctorIds") List<Long> doctorIds, @Param("designation") String designation);
	
	@Query(value = "SELECT d.Doctor_Id, d.Name, d.Phoneno, d.Email_Id, d.Degree, d.Desigination, d.Address, h.Visiting_Hours FROM hospital_doctor_details h LEFT JOIN doctor_details AS d ON d.Doctor_Id = h.Doctor_Id", nativeQuery = true)
	public List<Object[]> findAllHospitalDoctors();
	
	@Query(value = "SELECT d.Doctor_Id, d.Name, d.Phoneno, d.Email_Id, d.Degree, d.Desigination, d.Address, h.Visiting_Hours FROM hospital_doctor_details h LEFT JOIN doctor_details AS d ON d.Doctor_Id = h.Doctor_Id WHERE d.Name LIKE %:doctorName% ORDER BY d.Name ASC", nativeQuery = true)
	public List<Object[]> findByDoctorName(@Param("doctorName") String doctorName);
	
	@Query(value = "SELECT d.Doctor_Id, d.Name, d.Phoneno, d.Email_Id, d.Degree, d.Desigination, d.Address, h.Visiting_Hours FROM hospital_doctor_details h LEFT JOIN doctor_details AS d ON d.Doctor_Id = h.Doctor_Id WHERE d.Doctor_Id =:DId ORDER BY d.Name ASC", nativeQuery = true)
	public List<Object[]> findByHospitalDoctorId(@Param("DId") Long DId);
}
