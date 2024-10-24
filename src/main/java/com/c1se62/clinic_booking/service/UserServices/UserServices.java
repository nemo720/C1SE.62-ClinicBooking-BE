package com.c1se62.clinic_booking.service.UserServices;

import com.c1se62.clinic_booking.dto.request.LoginRequest;
import com.c1se62.clinic_booking.dto.request.RegisterRequest;
import com.c1se62.clinic_booking.dto.response.AuthenticationResponse;
import com.c1se62.clinic_booking.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserServices {
    UserResponse register(RegisterRequest request) ;
    String logout(String refreshToken) throws Exception;
}
