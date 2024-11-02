package com.c1se62.clinic_booking.service.AppointmentServices;

import com.c1se62.clinic_booking.dto.request.AppointmentRequest;
import com.c1se62.clinic_booking.entity.Appointment;
import com.c1se62.clinic_booking.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentServices {
    String addAppointment(AppointmentRequest appointment);

}
