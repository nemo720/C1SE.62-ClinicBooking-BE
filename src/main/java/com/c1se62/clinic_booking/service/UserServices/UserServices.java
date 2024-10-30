package com.c1se62.clinic_booking.service.UserServices;

import com.c1se62.clinic_booking.dto.request.LoginRequest;
import com.c1se62.clinic_booking.dto.request.RegisterRequest;
import com.c1se62.clinic_booking.dto.response.AuthenticationResponse;
import com.c1se62.clinic_booking.dto.response.UserResponse;
import com.c1se62.clinic_booking.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    UserResponse register(RegisterRequest request) ;
    String logout(String refreshToken) throws Exception;
    List<User> findAll();
    void save(User User);
    User findByUsername(String username);

    List<User> findUserByNameContaining(String name);

    void delete(String username);
}
