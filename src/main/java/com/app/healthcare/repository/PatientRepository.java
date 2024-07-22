package com.app.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.healthcare.model.PatientEntity;

@Repository
public interface PatientRepository extends JpaRepository < PatientEntity, Long >
{
	@Query("SELECT u FROM PatientEntity u WHERE u.patientPhoneno =:mobilenumber")
	public Optional<PatientEntity> findByMobileNo(@Param("mobilenumber") Long mobilenumber);

	@Query("SELECT u FROM PatientEntity u WHERE u.patientUserName =:userName AND u.patientPassword =:userPassword")
	public PatientEntity loginUser(@Param("userName") String userName, @Param("userPassword") String userPassword);

	@Query("SELECT u FROM PatientEntity u WHERE u.patientId =:id")
	public PatientEntity findByPatientId(@Param("id") Long id);
	
	@Query("SELECT u.patientId FROM PatientEntity u WHERE u.patientId =:pId")
	public List<Long> findByPatientIdOnly(@Param("pId") Long pId);
	
}
