package com.c1se62.clinic_booking.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRequest {
    private Integer doctorId;
    private LocalDate bookingDate;
    private Integer timeSlotId;
    private Integer patientId;
    private String appointmentType;
    private String status;
}
