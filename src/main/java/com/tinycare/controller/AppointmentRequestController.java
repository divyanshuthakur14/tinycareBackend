package com.tinycare.controller;

import com.tinycare.dto.AppointmentApprovalDTO;
import com.tinycare.dto.AppointmentDTO;
import com.tinycare.dto.AppointmentRequestCreateDTO;
import com.tinycare.dto.AppointmentRequestDTO;
import com.tinycare.service.AppointmentRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class AppointmentRequestController {

    private final AppointmentRequestService service;

    public AppointmentRequestController(AppointmentRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentRequestDTO> create(@RequestBody AppointmentRequestCreateDTO dto,
                                                        Authentication auth) {
        return ResponseEntity.ok(service.create(dto, auth));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentRequestDTO>> getMyRequests(Authentication auth) {
        return ResponseEntity.ok(service.getMyRequests(auth));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<AppointmentRequestDTO>> getPending() {
        return ResponseEntity.ok(service.getAllPending());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam String status) {
        service.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<AppointmentDTO> approve(@PathVariable Long id,
                                                  @RequestBody AppointmentApprovalDTO dto) {
        return ResponseEntity.ok(service.approveAndCreateAppointment(id, dto.doctorName, dto.appointmentDate));
    }

}
