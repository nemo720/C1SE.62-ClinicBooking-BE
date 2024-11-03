package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.response.DoctorResponse;
import com.c1se62.clinic_booking.dto.response.TimeslotResponse;
import com.c1se62.clinic_booking.entity.TimeSlot;
import com.c1se62.clinic_booking.service.DoctorServices.DoctorServices;
import com.c1se62.clinic_booking.service.TimeSlotServices.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private TimeSlotServices timeSlotServices;
    @Autowired
    private DoctorServices doctorServices;
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
    @GetMapping("/getAlldoctor")
    public ResponseEntity<?> getAllDoctor() {
        try {
            List<DoctorResponse> doctorResponses = doctorServices.getAllDoctors();

            if (doctorResponses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No doctors found");
            }

            return ResponseEntity.status(HttpStatus.OK).body(doctorResponses);
        } catch (Exception e) {
            // Trả về lỗi với mã 500 nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching doctors.");
        }
    }
    @GetMapping("/getAlldoctorByDepartment")
    public ResponseEntity<?> getAllDoctorsByDepartment(@RequestParam("departmentname") String departmentName) {
        try {
            List<DoctorResponse> doctorResponses = doctorServices.getAllDoctorsByDepartment(departmentName);
            return ResponseEntity.status(HttpStatus.OK).body(doctorResponses);
        } catch (Exception e) {
            // Trả về lỗi với mã 404 nếu không tìm thấy Department
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
