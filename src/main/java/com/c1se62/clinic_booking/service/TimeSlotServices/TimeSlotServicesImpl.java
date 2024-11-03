package com.c1se62.clinic_booking.service.TimeSlotServices;

import com.c1se62.clinic_booking.dto.response.TimeslotResponse;
import com.c1se62.clinic_booking.entity.TimeSlot;
import com.c1se62.clinic_booking.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServicesImpl implements TimeSlotServices{
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Override
    public List<TimeslotResponse> getAvailableTimeSlots(Integer doctorId, String date) {
        LocalDate localDate = LocalDate.parse(date);
        LocalTime startTime = LocalTime.parse("00:00:00"); // Assuming your TimeSlots have time in this format
        LocalTime endTime = LocalTime.parse("23:59:59");
        List<TimeSlot> t =  timeSlotRepository.findByDoctorIdAndDateAndStatus(
                doctorId, startTime, endTime,TimeSlot.TimeSlotStatus.AVAILABLE);
        List<TimeslotResponse> res = t.stream()
                .map(timeSlot -> {
                    TimeslotResponse timeslotResponse = new TimeslotResponse();
                    timeslotResponse.setTimeSlotId(timeSlot.getTimeSlotId());
                    timeslotResponse.setDate(timeSlot.getDate());
                    timeslotResponse.setTimeStart(timeSlot.getTimeStart());
                    timeslotResponse.setTimeEnd(timeSlot.getTimeEnd());
                    return timeslotResponse;
                })
                .collect(Collectors.toList());

        return res;
    }
}
