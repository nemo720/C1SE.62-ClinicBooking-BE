package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    Appointment save(Appointment newAppointment);
}
