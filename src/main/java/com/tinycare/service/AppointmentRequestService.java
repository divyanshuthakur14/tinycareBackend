package com.tinycare.service;

import com.tinycare.dto.AppointmentDTO;
import com.tinycare.dto.AppointmentRequestCreateDTO;
import com.tinycare.dto.AppointmentRequestDTO;
import com.tinycare.model.Appointment;
import com.tinycare.model.AppointmentRequest;
import com.tinycare.model.User;
import com.tinycare.repository.AppointmentRepository;
import com.tinycare.repository.AppointmentRequestRepository;
import com.tinycare.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentRequestService {
    private final AppointmentRequestRepository requestRepo;
    private final UserRepository userRepo;
    private final AppointmentRepository appointmentRepo;

    public AppointmentRequestService(AppointmentRequestRepository requestRepo, UserRepository userRepo, AppointmentRepository appointmentRepo) {
        this.requestRepo = requestRepo;
        this.userRepo = userRepo;
        this.appointmentRepo = appointmentRepo;
    }

    public AppointmentRequestDTO create(AppointmentRequestCreateDTO dto, Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        AppointmentRequest ar = new AppointmentRequest();
        ar.setPreferredDate(dto.preferredDate);
        ar.setReason(dto.reason);
        ar.setUser(user);
        return new AppointmentRequestDTO(requestRepo.save(ar));
    }

    public List<AppointmentRequestDTO> getMyRequests(Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return requestRepo.findByUserId(user.getId()).stream()
                .map(AppointmentRequestDTO::new).collect(Collectors.toList());
    }

    public List<AppointmentRequestDTO> getAllPending() {
        return requestRepo.findByStatus("PENDING").stream()
                .map(AppointmentRequestDTO::new).collect(Collectors.toList());
    }

    public void updateStatus(Long id, String newStatus) {
        AppointmentRequest req = requestRepo.findById(id).orElseThrow();
        req.setStatus(newStatus.toUpperCase());
        requestRepo.save(req);
    }


    public AppointmentDTO approveAndCreateAppointment(Long requestId, String doctorName, LocalDateTime finalDate) {
        AppointmentRequest req = requestRepo.findById(requestId).orElseThrow();
        if (!"PENDING".equals(req.getStatus())) throw new RuntimeException("Already processed");

        req.setStatus("APPROVED");
        requestRepo.save(req);

        Appointment appt = new Appointment();
        appt.setDoctorName(doctorName);
        appt.setAppointmentDate(finalDate);
        appt.setReason(req.getReason());
        appt.setUser(req.getUser());

        return new AppointmentDTO(appointmentRepo.save(appt));
    }

}
