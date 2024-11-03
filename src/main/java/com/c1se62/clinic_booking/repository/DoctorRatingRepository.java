package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.DoctorRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DoctorRatingRepository extends JpaRepository<DoctorRating, Integer> {
    @Query("SELECT AVG(r.rating) FROM DoctorRating r WHERE r.doctor.doctorId = :doctorId")
    Double findAverageRatingByDoctorId(@Param("doctorId") Integer doctorId);
}
