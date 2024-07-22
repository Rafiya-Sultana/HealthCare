package com.app.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.healthcare.model.DoctorEntity;

@Repository
public interface DoctorRepository extends JpaRepository < DoctorEntity, Long >
{
	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorPhoneno =:mobilenumber")
	public Optional<DoctorEntity> findByMobileNo(@Param("mobilenumber") Long mobilenumber);

	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorUserName =:userName AND d.doctorPassword =:userPassword")
	public DoctorEntity loginUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

	@Query("SELECT d FROM DoctorEntity d ORDER BY d.doctorName ASC")
	public List<DoctorEntity> findAllDoctors();

	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorId IN (:doctorIds) ORDER BY d.doctorName ASC")
	public List<DoctorEntity> findHospitalDoctors(@Param("doctorIds") List<Long> doctorIds);
	
	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorId IN (:doctorIds) AND d.doctorDesigination LIKE %:designation% ORDER BY d.doctorName ASC")
	public List<DoctorEntity> findHospitalDoctorsByDesignation(@Param("doctorIds") List<Long> doctorIds, @Param("designation") String designation);
	
	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorId =:DId")
	public DoctorEntity findByDoctorId(@Param("DId") Long DId);
	
	@Query("SELECT d FROM DoctorEntity d WHERE d.doctorName LIKE %:doctorName% ORDER BY d.doctorName ASC")
	public List<DoctorEntity> findByDoctorName(@Param("doctorName") String doctorName);
}
