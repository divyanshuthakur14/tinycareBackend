package com.tinycare.service;

import com.tinycare.dto.BabyHealthRecordDTO;
import com.tinycare.exception.ResourceNotFoundException;
import com.tinycare.model.BabyHealthRecord;
import com.tinycare.model.Role;
import com.tinycare.model.User;
import com.tinycare.repository.BabyHealthRecordRepository;
import com.tinycare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BabyHealthRecordService {

    @Autowired
    private BabyHealthRecordRepository babyRepo;

    @Autowired
    private UserRepository userRepo;

    public BabyHealthRecord createRecord(BabyHealthRecordDTO dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BabyHealthRecord record = new BabyHealthRecord();
        record.setBabyName(dto.getBabyName());
        record.setAgeInMonths(dto.getAgeInMonths());
        record.setWeight(dto.getWeight());
        record.setNotes(dto.getNotes());
        record.setUser(user);

        return babyRepo.save(record);
    }

    public List<BabyHealthRecordDTO> getBabiesForUser(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<BabyHealthRecord> babies = babyRepo.findByUser(user);
        return babies.stream().map(BabyHealthRecordDTO::new).toList();
    }

    public BabyHealthRecord updateBaby(Long id, BabyHealthRecordDTO dto, String email) {
        BabyHealthRecord baby = babyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!baby.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }

        baby.setBabyName(dto.getBabyName());
        baby.setAgeInMonths(dto.getAgeInMonths());
        baby.setWeight(dto.getWeight());
        baby.setNotes(dto.getNotes());

        return babyRepo.save(baby);
    }

    public void deleteBaby(Long id, String email) {
        BabyHealthRecord baby = babyRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Record not found"));

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!baby.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }
        babyRepo.delete(baby);
    }

    public List<BabyHealthRecordDTO> searchByName(String name, Long userId) {
        List<BabyHealthRecord> records = babyRepo.findByUserIdAndBabyNameContainingIgnoreCase(userId, name);
        return records.stream().map(BabyHealthRecordDTO::new).toList();
    }

    public List<BabyHealthRecordDTO> searchByAge(Integer age, Long userId) {
        List<BabyHealthRecord> records = babyRepo.findByUserIdAndAgeInMonths(userId, age);
        List<BabyHealthRecord> all = babyRepo.findByUserId(userId);
        for (BabyHealthRecord b : all) {
            System.out.println("Found: " + b.getBabyName() + " - " + b.getAgeInMonths());
        }
        return records.stream().map(BabyHealthRecordDTO::new).toList();
    }
}
