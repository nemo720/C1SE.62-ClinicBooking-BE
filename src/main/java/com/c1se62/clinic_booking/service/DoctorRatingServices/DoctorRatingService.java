package com.c1se62.clinic_booking.service.DoctorRatingServices;

import com.c1se62.clinic_booking.dto.DoctorRatingDTO;
import com.c1se62.clinic_booking.dto.request.RatingRequest;
import com.c1se62.clinic_booking.entity.DoctorRating;
import com.c1se62.clinic_booking.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface DoctorRatingService {
    DoctorRatingDTO addRating(RatingRequest ratingRequest, User user);
}
