package com.c1se62.clinic_booking.service.DoctorServices;

import com.c1se62.clinic_booking.dto.response.DoctorResponse;
import com.c1se62.clinic_booking.entity.Doctor;

import java.util.List;

public interface DoctorServices {
    List<DoctorResponse> getAllDoctors() throws Exception;
    List<DoctorResponse> getAllDoctorsByDepartment(String depamentname) throws Exception;

}
