package com.c1se62.clinic_booking.service.DoctorServices;

import com.c1se62.clinic_booking.dto.response.DoctorRatingResponse;
import com.c1se62.clinic_booking.dto.response.DoctorResponse;
import com.c1se62.clinic_booking.entity.Department;
import com.c1se62.clinic_booking.entity.Doctor;
import com.c1se62.clinic_booking.entity.DoctorRating;
import com.c1se62.clinic_booking.repository.DepartmentRepository;
import com.c1se62.clinic_booking.repository.DoctorRatingRepository;
import com.c1se62.clinic_booking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DoctorServicesImpl implements DoctorServices {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private DoctorRatingRepository doctorRatingRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public List<DoctorResponse> getAllDoctors() throws Exception {
        List<Doctor> doctors = doctorRepository.getAllDoctor();
        List<DoctorResponse> doctorResponses = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorResponse d = new DoctorResponse();
            d.setDoctorId(doctor.getDoctorId());
            d.setBio(doctor.getBio());
            d.setSpeciality(doctor.getSpeciality());
            d.setDoctorName(doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName());
            d.setDepartment(doctor.getDepartment().getName()); // Chỉ lấy tên department

            Double averageRating = doctorRatingRepository.findAverageRatingByDoctorId(doctor.getDoctorId());
            // Kiểm tra giá trị trả về trước khi sử dụng
            if (averageRating == null) {
                d.setRating(0.0); // Nếu không có đánh giá, đặt giá trị mặc định là 0
            } else {
                d.setRating(averageRating); // Nếu có giá trị trung bình, đặt vào thuộc tính rating của d
            }

            doctorResponses.add(d);
        }
        return doctorResponses;
    }

    @Override
    public List<DoctorResponse> getAllDoctorsByDepartment(String depamentname) throws Exception {
        Department department = departmentRepository.findByName(depamentname);

        // Kiểm tra nếu department không tồn tại
        if (department == null) {
            throw new Exception("Department with name '" + depamentname + "' not found.");
        }

        List<Doctor> doctors = doctorRepository.getAllDoctorByDepartment(department.getDepartmentId());
        List<DoctorResponse> doctorResponses = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorResponse d = new DoctorResponse();
            d.setDoctorId(doctor.getDoctorId());
            d.setBio(doctor.getBio());
            d.setSpeciality(doctor.getSpeciality());
            d.setDoctorName(doctor.getUser().getFirstName() + " " + doctor.getUser().getLastName());
            d.setDepartment(doctor.getDepartment().getName()); // Chỉ lấy tên department

            Double averageRating = doctorRatingRepository.findAverageRatingByDoctorId(doctor.getDoctorId());

            // Kiểm tra giá trị trả về của rating trước khi sử dụng
            if (averageRating == null) {
                d.setRating(0.0); // Nếu không có đánh giá, đặt giá trị mặc định là 0
            } else {
                d.setRating(averageRating); // Nếu có giá trị trung bình, đặt vào thuộc tính rating của d
            }

            doctorResponses.add(d);
        }
        return doctorResponses;
    }

    @Override
    public DoctorResponse getDoctorById(int id) throws Exception {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    DoctorResponse response = new DoctorResponse();
                    response.setDoctorId(doctor.getDoctorId());
                    response.setBio(doctor.getBio());
                    response.setSpeciality(doctor.getSpeciality());
                    response.setDoctorName(doctor.getUser () != null ?
                            doctor.getUser ().getFirstName() + " " + doctor.getUser ().getLastName() : "Unknown");
                    response.setDepartment(doctor.getDepartment() != null ?
                            doctor.getDepartment().getName() : "Unknown");
                    Double averageRating = doctorRatingRepository.findAverageRatingByDoctorId(doctor.getDoctorId());
                    // Kiểm tra giá trị trả về trước khi sử dụng
                    if (averageRating == null) {
                        response.setRating(0.0); // Nếu không có đánh giá, đặt giá trị mặc định là 0
                    } else {
                        response.setRating(averageRating); // Nếu có giá trị trung bình, đặt vào thuộc tính rating của d
                    }
                    return response;
                })
                .orElseThrow(() -> new Exception("Doctor not found with id: " + id));
    }

    @Override
    public List<DoctorRatingResponse> getRatingsByDoctorId(int doctorId) {
        List<DoctorRating> ratings = doctorRatingRepository.findByDoctor(doctorId);
        return ratings.stream()
                .map(rating -> new DoctorRatingResponse(rating.getRatingId(), rating.getRating(), rating.getComment(), rating.getCreatedAt()))
                .collect(Collectors.toList());
    }


}
