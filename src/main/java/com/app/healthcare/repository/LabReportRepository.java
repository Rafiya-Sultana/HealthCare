package com.app.healthcare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.app.healthcare.model.LabReportEntity;

@Repository
public interface LabReportRepository extends JpaRepository < LabReportEntity, Long >
{
	@Query("SELECT u FROM LabReportEntity u WHERE u.appointmentId =:aId ORDER BY u.RecordedDate DESC")
	public List<LabReportEntity> findByAppointmentId(@Param("aId") Long aId);
}
