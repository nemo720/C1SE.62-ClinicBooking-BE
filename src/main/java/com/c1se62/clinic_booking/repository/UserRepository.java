package com.c1se62.clinic_booking.repository;

import com.c1se62.clinic_booking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    void deleteByUserId(Long id);
    Optional<User> findById(Long id);

}
