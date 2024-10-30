package com.c1se62.clinic_booking.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotNull(message = "Vui lòng nhập mật khẩu và tên đăng nhập")
    @Size(min = 3, message = "Mật khẩu phải ít nhất 3 kí tự")
    private String username;
    @NotNull(message = "Vui lòng nhập mật khẩu và tên đăng nhập")
    @Size(min = 8, message = "mật khẩu phải ít nhất 8 kí tự")
    private String password;
    private Set<String> role;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
