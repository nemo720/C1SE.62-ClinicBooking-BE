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
import com.c1se62.clinic_booking.service.Email.EmailService;
import jakarta.mail.MessagingException;
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
    @Autowired
    private EmailService emailService;
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

        Appointment newAppointment = new Appointment();
        newAppointment.setDoctor(doctor);
        newAppointment.setUser(user);
        newAppointment.setTimeSlot(timeSlot);
        newAppointment.setAppointmentType("Khám bệnh");
        newAppointment.setStatus("Đang xử lí");
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        timeSlot.setStatus(TimeSlot.TimeSlotStatus.BOOKED);
        timeSlotRepository.save(timeSlot);
        String subject = "Xác nhận lịch hẹn khám";
        String body = "<html>" +
                "<head>" +
                "<style>" +
                "body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0; }" +
                ".container { background-color: #ffffff; margin: 20px auto; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); max-width: 600px; }" +
                "h2 { color: #2c3e50; }" +
                "p { color: #34495e; line-height: 1.6; }" +
                "table { width: 100%; border-collapse: collapse; margin-top: 20px; }" +
                "th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }" +
                "th { background-color: #2c3e50; color: #ffffff; }" +
                "td { background-color: #ecf0f1; }" +
                ".footer { margin-top: 20px; padding-top: 20px; border-top: 1px solid #ddd; text-align: center; color: #95a5a6; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>Chào " + user.getFirstName() + ",</h2>" +
                "<p>Chúng tôi xin thông báo rằng lịch hẹn khám của bạn đã được xác nhận. Dưới đây là thông tin chi tiết về lịch hẹn của bạn:</p>" +
                "<table>" +
                "<tr><th>Bác sĩ</th><td>" + doctor.getUser().getFirstName() + "</td></tr>" +
                "<tr><th>Thời gian</th><td>" + appointment.getBookingDate() + " lúc " + appointment.getTimeStart() + "</td></tr>" +
                "</table>" +
                "<p class='footer'>Chúng tôi hy vọng được chào đón bạn tại phòng khám. Nếu bạn có bất kỳ câu hỏi nào hoặc cần thay đổi lịch hẹn, vui lòng liên hệ với chúng tôi.</p>" +
                "<p class='footer'>Trân trọng,<br>Đội ngũ Clinic</p>" +
                "</div>" +
                "</body>" +
                "</html>";

        try {
            emailService.sendHtmlEmail(user.getEmail(), subject, body);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "Appointment created successfully! Appointment ID: " + savedAppointment.getAppointmentId();
    }

}
