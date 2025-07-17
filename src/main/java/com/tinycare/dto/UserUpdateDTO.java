package com.tinycare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {

    private String name;
    private String email;
    private String password;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Name is required")
    @NotBlank(message = "Email is required")

    public UserUpdateDTO() {
    }

    public UserUpdateDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
