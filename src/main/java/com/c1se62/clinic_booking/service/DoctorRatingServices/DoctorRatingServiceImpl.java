package com.c1se62.clinic_booking.service.DoctorRatingServices;

import com.c1se62.clinic_booking.dto.DoctorRatingDTO;
import com.c1se62.clinic_booking.dto.request.RatingRequest;
import com.c1se62.clinic_booking.entity.Doctor;
import com.c1se62.clinic_booking.entity.DoctorRating;
import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.repository.AppointmentRepository;
import com.c1se62.clinic_booking.repository.DoctorRatingRepository;
import com.c1se62.clinic_booking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorRatingServiceImpl implements DoctorRatingService{
    @Autowired
    private DoctorRatingRepository doctorRatingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public DoctorRatingDTO addRating(RatingRequest ratingRequest, User user) {
        // Kiểm tra sự tồn tại của bác sĩ
        Doctor doctor = doctorRepository.findById(ratingRequest.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Bác sĩ không tồn tại"));

        // Kiểm tra người dùng đã hoàn thành lịch hẹn với bác sĩ này hay chưa
        boolean hasAppointment = appointmentRepository.existsByUserIdAndDoctorIdAndStatus(user.getUserId(), doctor.getDoctorId(), "COMPLETED");
        if (!hasAppointment) {
            throw new RuntimeException("Bạn cần hoàn thành một lịch hẹn với bác sĩ này trước khi đánh giá.");
        }

        DoctorRating doctorRating = new DoctorRating();
        doctorRating.setDoctor(doctor);
        doctorRating.setPatient(user);
        doctorRating.setRating(ratingRequest.getRating());
        doctorRating.setComment(ratingRequest.getComment());
        doctorRatingRepository.save(doctorRating);
        DoctorRatingDTO doctorRatingDTO = new DoctorRatingDTO();
        doctorRatingDTO.setDoctorId(ratingRequest.getDoctorId());
        doctorRatingDTO.setPatientId(user.getUserId());
        doctorRatingDTO.setRating(ratingRequest.getRating());
        doctorRatingDTO.setComment(ratingRequest.getComment());
        try {
            return doctorRatingDTO;
        } catch (Exception e) {
            throw new RuntimeException("Không thể lưu đánh giá vào cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
