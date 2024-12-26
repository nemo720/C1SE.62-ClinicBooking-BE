package com.c1se62.clinic_booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {
    private Integer appointmentId;
    private Integer doctor;
    private LocalDate date;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String doctorName;
    private String status;
    private Integer user;
    private String name;
    private String department;
}
