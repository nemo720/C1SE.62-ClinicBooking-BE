package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.request.AppointmentRequest;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.service.AppointmentServices.AppointmentServices;
import com.c1se62.clinic_booking.service.AuthenticationServices.AuthenticationServices;
import com.c1se62.clinic_booking.service.UserServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServices userServices;
    @Autowired
    private AuthenticationServices authenticationService;
    @Autowired
    private AppointmentServices appointmentServices;

    @PostMapping("/booking")
    public ResponseEntity<String> addBooking(@RequestBody AppointmentRequest appointmentRequest) {
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if(!(authentication instanceof AnonymousAuthenticationToken)){
                appointmentServices.addAppointment(appointmentRequest);
                return ResponseEntity.status(HttpStatus.OK).body("Đặt lịch khám thành công");
            }else{
                return ResponseEntity.status(403).body("You must login");
            }
        }catch (Exception e){
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
