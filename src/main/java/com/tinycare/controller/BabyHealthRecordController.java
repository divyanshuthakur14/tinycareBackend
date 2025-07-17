package com.tinycare.controller;

import com.tinycare.dto.BabyHealthRecordDTO;
import com.tinycare.service.BabyHealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.tinycare.model.User;
import com.tinycare.service.userService;

import java.util.List;

@RestController
@RequestMapping("/api/babies")
public class BabyHealthRecordController {

    @Autowired
    private BabyHealthRecordService babyService;
    private final userService userservice;

    public BabyHealthRecordController(BabyHealthRecordService babyService, userService userservice) {
        this.babyService = babyService;
        this.userservice = userservice;
    }

    @PostMapping
    public ResponseEntity<?> createBabyRecord(@RequestBody BabyHealthRecordDTO dto) {
        return ResponseEntity.ok(babyService.createRecord(dto));
    }

    @GetMapping
    public ResponseEntity<List<BabyHealthRecordDTO>> getAllBabiesForUser(Authentication auth) {
        String email = auth.getName();
        List<BabyHealthRecordDTO> babies = babyService.getBabiesForUser(email);
        return ResponseEntity.ok(babies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBaby(@PathVariable Long id, @RequestBody BabyHealthRecordDTO dto, Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(babyService.updateBaby(id, dto, email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBaby(@PathVariable Long id, Authentication auth) {
        String email = auth.getName();
        babyService.deleteBaby(id, email);
        return ResponseEntity.ok("Baby record deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<BabyHealthRecordDTO>> search(@RequestParam(required = false) String name, @RequestParam(required = false) Integer age, Authentication authentication) {
        User user = userservice.getUserByEmail(authentication.getName());
        if (name != null) {
            return ResponseEntity.ok(babyService.searchByName(name, user.getId()));
        } else if (age != null) {
            return ResponseEntity.ok(babyService.searchByAge(age, user.getId()));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
