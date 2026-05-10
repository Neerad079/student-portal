package com.portal.studentportal.service;

import com.portal.studentportal.dtos.AdminDto;
import com.portal.studentportal.entity.Student;
import com.portal.studentportal.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final StudentRepository studentRepository;

    public AdminService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<AdminDto> getAllStudents() {
       return studentRepository.findAll().stream()
               .map(student -> AdminDto.builder()
                       .id(student.getId())
                       .username(student.getUsername())
                       .email(student.getEmail())
                       .roll_no(student.getRoll_no())
                       .role(student.getRole())
                       .build())
               .collect(Collectors.toList());
    }
    // You can add more admin methods here later (deleteStudent, updateRole, etc.)
}
