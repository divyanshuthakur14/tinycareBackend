package com.tinycare.controller;

import com.tinycare.dto.AppointmentApprovalDTO;
import com.tinycare.dto.AppointmentDTO;
import com.tinycare.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentDTO> create(@RequestBody AppointmentApprovalDTO dto, Authentication auth) {
        return ResponseEntity.ok(service.create(dto, auth));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(Authentication auth) {
        return ResponseEntity.ok(service.getUserAppointments(auth));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long id,
                                                 @RequestBody AppointmentApprovalDTO dto,
                                                 Authentication auth) {
        return ResponseEntity.ok(service.update(id, dto, auth));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) {
        service.delete(id, auth);
        return ResponseEntity.noContent().build();
    }
}
