package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.response.TimeslotResponse;
import com.c1se62.clinic_booking.entity.TimeSlot;
import com.c1se62.clinic_booking.service.TimeSlotServices.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private TimeSlotServices timeSlotServices;

    @GetMapping("/{doctorId}/timeslots/{date}")
    public ResponseEntity<List<TimeslotResponse>> getAvailableTimeSlots(
            @PathVariable Integer doctorId,
            @PathVariable String date) {
       try{
        List<TimeslotResponse> availableTimeSlots = timeSlotServices.getAvailableTimeSlots(doctorId, date);
        return ResponseEntity.ok(availableTimeSlots);
       }
       catch (Exception e) {
           // Handle different types of exceptions more specifically if needed
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request: " + e.getMessage());
       }
    }
}
