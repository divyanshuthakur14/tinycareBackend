package com.tinycare.service;

import com.tinycare.dto.AppointmentApprovalDTO;
import com.tinycare.dto.AppointmentDTO;
import com.tinycare.model.Appointment;
import com.tinycare.model.User;
import com.tinycare.repository.AppointmentRepository;
import com.tinycare.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
@Service
public class AppointmentService {
    private final AppointmentRepository repo;
    private final UserRepository userRepo;

    public AppointmentService(AppointmentRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public AppointmentDTO create(AppointmentApprovalDTO dto, Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        Appointment a = new Appointment();
        a.setDoctorName(dto.doctorName);
        a.setAppointmentDate(dto.appointmentDate);
        a.setReason("Manual Entry"); // or take as extra field in AppointmentApprovalDTO
        a.setUser(user);
        return new AppointmentDTO(repo.save(a));
    }

    public List<AppointmentDTO> getUserAppointments(Authentication auth) {
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        return repo.findByUserId(user.getId())
                .stream().map(AppointmentDTO::new)
                .collect(Collectors.toList());
    }

    public AppointmentDTO update(Long id, AppointmentApprovalDTO dto, Authentication auth) {
        Appointment a = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        if (!a.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN"))
            throw new RuntimeException("Unauthorized");

        a.setDoctorName(dto.doctorName);
        a.setAppointmentDate(dto.appointmentDate);
        a.setReason("Updated by admin");
        return new AppointmentDTO(repo.save(a));
    }

    public void delete(Long id, Authentication auth) {
        Appointment a = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        User user = userRepo.findByEmail(auth.getName()).orElseThrow();
        if (!a.getUser().getId().equals(user.getId()) && !user.getRole().equals("ADMIN"))
            throw new RuntimeException("Unauthorized");
        repo.delete(a);
    }
}
