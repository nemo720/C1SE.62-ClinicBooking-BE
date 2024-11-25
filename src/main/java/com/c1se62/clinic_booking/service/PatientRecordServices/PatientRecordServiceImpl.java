package com.c1se62.clinic_booking.service.PatientRecordServices;

import com.c1se62.clinic_booking.dto.request.PatientRecordCreatedDTO;
import com.c1se62.clinic_booking.dto.response.PatientRecordResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PatientRecordServiceImpl implements PatientRecordService{

    @Override
    public PatientRecordResponseDTO getPatientRecordById(Integer recordId) {
        return null;
    }

    @Override
    public List<PatientRecordResponseDTO> getPatientRecordsByPatientId(Integer patientId) {
        return List.of();
    }

    @Override
    public List<PatientRecordResponseDTO> getPatientRecordsByDoctorId(Integer doctorId) {
        return List.of();
    }

    @Override
    public List<PatientRecordResponseDTO> getPatientRecordsByDoctorIdAndPatientId(Integer doctorId, Integer patientId) {
        return List.of();
    }

    @Override
    public PatientRecordResponseDTO createPatientRecord(PatientRecordCreatedDTO patientRecordCreatedDTO) {
        return null;
    }

    @Override
    public PatientRecordResponseDTO updatePatientRecord(Integer recordId, PatientRecordCreatedDTO patientRecordCreatedDTO) {
        return null;
    }

    @Override
    public void deletePatientRecordById(Integer recordId) {
    }
}
