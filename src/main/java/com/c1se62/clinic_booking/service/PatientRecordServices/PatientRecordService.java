package com.c1se62.clinic_booking.service.PatientRecordServices;

import com.c1se62.clinic_booking.dto.request.PatientRecordCreatedDTO;
import com.c1se62.clinic_booking.dto.response.PatientRecordResponseDTO;

import java.util.List;

public interface PatientRecordService {
    PatientRecordResponseDTO getPatientRecordById(Integer recordId);
    List<PatientRecordResponseDTO> getPatientRecordsByPatientId(Integer patientId);
    List<PatientRecordResponseDTO> getPatientRecordsByDoctorId(Integer doctorId);
    List<PatientRecordResponseDTO> getPatientRecordsByDoctorIdAndPatientId(Integer doctorId, Integer patientId);

    PatientRecordResponseDTO createPatientRecord(PatientRecordCreatedDTO patientRecordCreatedDTO);
    void deletePatientRecordById(Integer recordId);
}
