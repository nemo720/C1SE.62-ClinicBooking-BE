package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.request.DoctorRequest;
import com.c1se62.clinic_booking.dto.request.UserRequest;
import com.c1se62.clinic_booking.dto.response.DoctorRatingResponse;
import com.c1se62.clinic_booking.dto.response.DoctorResponse;
import com.c1se62.clinic_booking.dto.response.TimeslotResponse;
import com.c1se62.clinic_booking.entity.Doctor;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.repository.DoctorRepository;
import com.c1se62.clinic_booking.service.DoctorServices.DoctorServices;
import com.c1se62.clinic_booking.service.TimeSlotServices.TimeSlotServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/{doctorId}/timeslot/{date}")
    public ResponseEntity<List<TimeslotResponse>> getAvailableTimeSlots(
            @PathVariable Integer doctorId,
            @PathVariable String date) {
        try {
            List<TimeslotResponse> availableTimeSlots = timeSlotServices.getAvailableTimeSlots(doctorId, date);
            return ResponseEntity.ok(availableTimeSlots);
        } catch (IllegalArgumentException e) {
            // Handle invalid doctorId or date
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while fetching timeslots: " + e.getMessage());
        }
    }

    @GetMapping("/getAlldoctor")
    public ResponseEntity<?> getAllDoctors(@RequestParam(value = "departmentname", required = false) String departmentName) {
        try {
            List<DoctorResponse> doctorResponses;

            if (departmentName != null && !departmentName.isEmpty()) {
                // Nếu có departmentName, tìm bác sĩ theo khoa
                doctorResponses = doctorServices.getAllDoctorsByDepartment(departmentName);
                return ResponseEntity.ok(doctorResponses);
            } else {
                // Nếu không có departmentName, lấy tất cả bác sĩ
                doctorResponses = doctorServices.getAllDoctors();
                return ResponseEntity.ok(doctorResponses);
            }
        } catch (Exception e) {
            // Trả về lỗi với mã 500 nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching doctors: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable int id) {
        try {
            DoctorResponse doctorResponse = doctorServices.getDoctorById(id);
            return new ResponseEntity<>(doctorResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{id}/ratings")
    public ResponseEntity<List<DoctorRatingResponse>> getRatingsByDoctorId(@PathVariable int id) {
        List<DoctorRatingResponse> ratings = doctorServices.getRatingsByDoctorId(id);
        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @PutMapping("/updateDoctor")
    public ResponseEntity<String> updateDoctor(@RequestBody @Valid DoctorRequest doctorRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                int doctorIdStr = jwt.getClaim("sub");
                Doctor doctor = doctorRepository.findById(doctorIdStr).orElse(null);

                if (doctor == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bác sĩ không tồn tại");
                }

                // Gọi service để cập nhật thông tin Bác sĩ
                String responseMessage = doctorServices.updateDoctor(doctorRequest,doctor.getDoctorId());

                if (responseMessage.equals("Cập nhật thông tin bác sĩ thành công")) {
                    return ResponseEntity.ok().body(responseMessage);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating doctor: " + e.getMessage());
        }
    }

}