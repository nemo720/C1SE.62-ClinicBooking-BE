package com.c1se62.clinic_booking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientRecordResponseDTO {
    private Integer recordId;
    private Integer patientId;
    private Integer doctorId;
    private String recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
}
