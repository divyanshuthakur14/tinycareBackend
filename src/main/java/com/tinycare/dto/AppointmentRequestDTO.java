package com.tinycare.dto;

import com.tinycare.model.AppointmentRequest;
import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    public Long id;
    public LocalDateTime preferredDate;
    public String reason;
    public String status;
    public LocalDateTime createdAt;

    public AppointmentRequestDTO(AppointmentRequest req) {
        this.id = req.getId();
        this.preferredDate = req.getPreferredDate();
        this.reason = req.getReason();
        this.status = req.getStatus();
        this.createdAt = req.getCreatedAt();
    }
}
