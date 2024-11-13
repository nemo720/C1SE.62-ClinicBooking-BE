package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment save(Appointment newAppointment);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Appointment a " +
            "WHERE a.user.userId = :userId " +
            "AND a.doctor.doctorId = :doctorId " +
            "AND a.status = :status")
    boolean existsByUserIdAndDoctorIdAndStatus(Integer userId, Integer doctorId, String status);



}
