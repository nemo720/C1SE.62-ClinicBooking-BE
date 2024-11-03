package com.c1se62.clinic_booking.service.AppointmentServices;

import com.c1se62.clinic_booking.dto.request.AppointmentRequest;
import com.c1se62.clinic_booking.entity.Appointment;
import com.c1se62.clinic_booking.entity.Doctor;
import com.c1se62.clinic_booking.entity.TimeSlot;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.mapper.AppointmentMapper;
import com.c1se62.clinic_booking.repository.AppointmentRepository;
import com.c1se62.clinic_booking.repository.DoctorRepository;
import com.c1se62.clinic_booking.repository.TimeSlotRepository;
import com.c1se62.clinic_booking.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentServicesImpl implements AppointmentServices{
    @Autowired
    AppointmentMapper appointmentMapper;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public String addAppointment(AppointmentRequest appointment,User user) {
        if (appointment.getDoctorId() == null ||  appointment.getTimeStart() == null ||
                appointment.getBookingDate() == null) {
            try {
                throw new BadRequestException("Invalid appointment request: missing required fields.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findByDoctorIdAndDateAndTimeStart(appointment.getDoctorId(), appointment.getTimeStart(), appointment.getBookingDate());
        if (timeSlotOptional.isEmpty()) {
            try {
                throw new BadRequestException("Invalid time slot ID.");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        TimeSlot timeSlot = timeSlotOptional.get();

        if (timeSlot.getStatus() != TimeSlot.TimeSlotStatus.AVAILABLE) {
            try {
                throw new BadRequestException("Vui lòng đặt vào thời gian khác");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctorId());
        if (doctorOptional.isEmpty()) {
            try {
                throw new BadRequestException("Bác sĩ không tồn tại");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        Doctor doctor = doctorOptional.get();

        Appointment newAppointment = appointmentMapper.toAppointment(appointment);
        newAppointment.setDoctor(doctor);
        newAppointment.setUser(user);
        newAppointment.setTimeSlot(timeSlot);
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        timeSlot.setStatus(TimeSlot.TimeSlotStatus.BOOKED);
        timeSlotRepository.save(timeSlot);
        return "Appointment created successfully! Appointment ID: " + savedAppointment.getAppointmentId();
    }
}
