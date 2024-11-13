package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
    @Query("SELECT ts FROM TimeSlot ts WHERE ts.doctor.doctorId = :doctorId " +
            "AND ts.timeStart >= :startDate AND ts.timeEnd <= :endDate "+
            "AND ts.status = :status")
    List<TimeSlot> findByDoctorIdAndDateAndStatus(@Param("doctorId") Integer doctorId,
                                                  @Param("startDate") LocalTime startDate,
                                                  @Param("endDate") LocalTime endDate,
                                                  @Param("status") TimeSlot.TimeSlotStatus status);
    @Query("SELECT ts FROM TimeSlot ts WHERE ts.doctor.doctorId = :doctorId " +
            "AND ts.timeStart <= :startDate AND ts.timeEnd >= :startDate AND ts.date <= :Date ")
    Optional<TimeSlot> findByDoctorIdAndDateAndTimeStart(@Param("doctorId") Integer doctorId,
                                                         @Param("startDate") LocalTime startDate,
                                                         @Param("Date") LocalDate Date
                                                         );

}