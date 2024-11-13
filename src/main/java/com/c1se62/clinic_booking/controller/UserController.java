package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.dto.DoctorRatingDTO;
import com.c1se62.clinic_booking.dto.request.AppointmentRequest;
import com.c1se62.clinic_booking.dto.request.ForgotPasswordRequest;
import com.c1se62.clinic_booking.dto.request.RatingRequest;
import com.c1se62.clinic_booking.dto.request.UserRequest;
import com.c1se62.clinic_booking.entity.DoctorRating;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.repository.UserRepository;
import com.c1se62.clinic_booking.service.AppointmentServices.AppointmentServices;
import com.c1se62.clinic_booking.service.AuthenticationServices.AuthenticationServices;
import com.c1se62.clinic_booking.service.DoctorRatingServices.DoctorRatingService;
import com.c1se62.clinic_booking.service.Email.EmailService;
import com.c1se62.clinic_booking.service.UserServices.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.ErrorResponse;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRatingService doctorRatingService;

    @PostMapping("/booking")
    public ResponseEntity<String> addBooking(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");
                User user = userRepository.findByUsername(userIdStr).orElse(null);
                appointmentServices.addAppointment(appointmentRequest, user);
                return ResponseEntity.status(HttpStatus.OK).body("Đặt lịch khám thành công");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

    @PostMapping("/forgotpassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");

                User user = userRepository.findByUsername(userIdStr).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
                }

                String responseMessage = userServices.forgotPassword(forgotPasswordRequest, user);

                if (responseMessage.equals("Mật khẩu thay đổi thành công")) {
                    return ResponseEntity.ok().body(responseMessage);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }



    @PostMapping("/rating")
    public ResponseEntity<?> addRating(@RequestBody RatingRequest request) {
        try {
            // Kiểm tra nếu người dùng đã đăng nhập
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");
                User user = userRepository.findByUsername(userIdStr).orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

                // Gọi service để thêm đánh giá
                DoctorRatingDTO doctorRating = doctorRatingService.addRating(request, user);
                return ResponseEntity.status(HttpStatus.OK).body(doctorRating);
            } else {
                // Nếu người dùng chưa đăng nhập
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (RuntimeException e) {
            // Xử lý ngoại lệ từ service (ví dụ: không có bác sĩ, không có lịch hẹn hoàn thành)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lỗi: " + e.getMessage());
        } catch (Exception e) {
            // Xử lý ngoại lệ tổng quát
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi hệ thống: " + e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
                String userIdStr = jwt.getClaim("sub");
                User user = userRepository.findByUsername(userIdStr).orElse(null);

                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
                }

                // Gọi service để cập nhật thông tin người dùng
                String responseMessage = userServices.updateUser(request,user.getUserId());

                if (responseMessage.equals("Cập nhật thông tin người dùng thành công")) {
                    return ResponseEntity.ok().body(responseMessage);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn cần đăng nhập để thực hiện thao tác này");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi hệ thống: " + e.getMessage());
        }
    }

}

