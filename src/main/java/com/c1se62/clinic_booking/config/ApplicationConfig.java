package com.c1se62.clinic_booking.config;

import com.c1se62.clinic_booking.emuns.ERole;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@Slf4j
public class ApplicationConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if( userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();

                roles.add(ERole.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .role(roles)
                        .build();
                userRepository.save(user);
                log.warn("admin user has been created with default password:admin, please change it");
            }
        };
    }
}
