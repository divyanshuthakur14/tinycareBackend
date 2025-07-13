package com.tinycare.dto;

import com.tinycare.model.Appointment;
import java.time.LocalDateTime;

public class AppointmentDTO {
    public Long id;
    public String doctorName;
    public LocalDateTime appointmentDate;
    public String reason;
    public LocalDateTime createdAt;

    public AppointmentDTO(Appointment appt) {
        this.id = appt.getId();
        this.doctorName = appt.getDoctorName();
        this.appointmentDate = appt.getAppointmentDate();
        this.reason = appt.getReason();
        this.createdAt = appt.getCreatedAt();
    }
}
