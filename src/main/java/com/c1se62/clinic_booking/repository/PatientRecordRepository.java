package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRecordRepository extends JpaRepository<PatientRecord, Integer> {
    @Modifying
    void deleteByPatientUserId(Integer userId);
}
