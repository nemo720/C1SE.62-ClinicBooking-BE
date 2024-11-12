package com.c1se62.clinic_booking.service.UserServices;

import com.c1se62.clinic_booking.dto.request.ForgotPasswordRequest;
import com.c1se62.clinic_booking.dto.request.RegisterRequest;
import com.c1se62.clinic_booking.dto.response.UserResponse;
import com.c1se62.clinic_booking.emuns.ERole;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.mapper.UserMapper;
import com.c1se62.clinic_booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse register(RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("ErrorCode.USER_EXISTED");
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(ERole.USER.name());
        user.setRole(roles);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request, User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // Kiểm tra mật khẩu cũ có đúng không
        boolean authenticated = passwordEncoder.matches(request.getOldPassword(), user.getPassword());

        if (authenticated) {
            // Nếu đúng, cập nhật mật khẩu mới
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);  // Lưu thay đổi vào cơ sở dữ liệu
            return "Mật khẩu thay đổi thành công";
        }

        // Nếu mật khẩu cũ không đúng
        return "Mật khẩu cũ không đúng";
    }

}
