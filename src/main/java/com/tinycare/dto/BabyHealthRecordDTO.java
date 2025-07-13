package com.tinycare.dto;

import com.tinycare.model.BabyHealthRecord;

import java.time.LocalDateTime;

public class BabyHealthRecordDTO {

    public BabyHealthRecordDTO() {

    }


    public BabyHealthRecordDTO(BabyHealthRecord baby) {
        this.id = baby.getId();
        this.babyName = baby.getBabyName();
        this.ageInMonths = baby.getAgeInMonths();
        this.weight = baby.getWeight();
        this.notes = baby.getNotes();
        this.createdAt = baby.getCreatedAt();
    }


    private Long id;
    private String babyName;
    private int ageInMonths;
    private double weight;
    private String notes;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public int getAgeInMonths() {
        return ageInMonths;
    }

    public void setAgeInMonths(int ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
