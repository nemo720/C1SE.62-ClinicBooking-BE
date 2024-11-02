package com.c1se62.clinic_booking.service.TimeSlotServices;

import com.c1se62.clinic_booking.dto.response.TimeslotResponse;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TimeSlotServices {
    public List<TimeslotResponse> getAvailableTimeSlots(Integer doctorId, String date);
}
