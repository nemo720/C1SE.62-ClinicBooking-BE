package com.c1se62.clinic_booking.service.UserServices;

import com.c1se62.clinic_booking.dto.request.ForgotPasswordRequest;
import com.c1se62.clinic_booking.dto.request.LoginRequest;
import com.c1se62.clinic_booking.dto.request.RegisterRequest;
import com.c1se62.clinic_booking.dto.request.UserRequest;
import com.c1se62.clinic_booking.dto.response.AuthenticationResponse;
import com.c1se62.clinic_booking.dto.response.UserResponse;
import com.c1se62.clinic_booking.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    UserResponse register(RegisterRequest request) ;
    String forgotPassword(ForgotPasswordRequest request,User users);
    String updateUser(UserRequest user,Integer userId);
}
