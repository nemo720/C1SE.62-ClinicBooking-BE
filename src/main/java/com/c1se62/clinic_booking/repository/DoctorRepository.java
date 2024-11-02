package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}