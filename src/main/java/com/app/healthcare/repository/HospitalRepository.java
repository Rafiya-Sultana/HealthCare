package com.app.healthcare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.healthcare.model.HospitalEntity;

@Repository
public interface HospitalRepository extends JpaRepository < HospitalEntity, Long >
{
	@Query("SELECT h FROM HospitalEntity h WHERE h.hospitalPhoneno =:mobilenumber")
	public Optional<HospitalEntity> findByMobileNo(@Param("mobilenumber") Long mobilenumber);

	@Query("SELECT h FROM HospitalEntity h WHERE h.hospitalUserName =:userName AND h.hospitalPassword =:userPassword")
	public HospitalEntity loginUser(@Param("userName") String userName, @Param("userPassword") String userPassword);
	
	@Query("SELECT h FROM HospitalEntity h WHERE h.hospitalId =:id")
	public HospitalEntity findByHospitalId(@Param("id") Long id);

	@Query("SELECT h FROM HospitalEntity h WHERE h.hospitalId IN (:ids) ORDER BY h.hospitalName ASC")
	public List<HospitalEntity> findByHospitalIds(@Param("ids") List<Long> ids);
}
