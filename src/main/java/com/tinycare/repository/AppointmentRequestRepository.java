package com.tinycare.repository;

import com.tinycare.model.AppointmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRequestRepository extends JpaRepository<AppointmentRequest, Long> {
    List<AppointmentRequest> findByUserId(Long userId);
    List<AppointmentRequest> findByStatus(String status);
}
