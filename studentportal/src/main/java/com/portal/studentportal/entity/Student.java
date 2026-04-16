package com.portal.studentportal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String roll_no;  // Manually set, not auto-generated
    private String email;
    private String username;
    private String password;
    private String role;  // "ADMIN" or "USER"
}
