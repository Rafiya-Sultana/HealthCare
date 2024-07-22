package com.app.healthcare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.healthcare.model.PrescriptionEntity;

@Repository
public interface PrescriptionRepository extends JpaRepository < PrescriptionEntity, Long >
{
	@Query("SELECT u FROM PrescriptionEntity u WHERE u.appointmentId =:aId ORDER BY u.RecordedDate DESC")
	public Optional<PrescriptionEntity> findByAppointmentId(@Param("aId") Long aId);
}
