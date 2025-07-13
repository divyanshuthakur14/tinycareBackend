package com.tinycare.repository;

import com.tinycare.model.BabyHealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tinycare.model.User;

import java.util.List;

public interface BabyHealthRecordRepository extends JpaRepository<BabyHealthRecord, Long> {
    List<BabyHealthRecord> findByUserId(Long userId);
    List<BabyHealthRecord> findByUser(User user);
    List<BabyHealthRecord> findByUserIdAndBabyNameContainingIgnoreCase(Long userId, String babyName);
    List<BabyHealthRecord> findByUserIdAndAgeInMonths(Long userId, Integer ageInMonths);
}

