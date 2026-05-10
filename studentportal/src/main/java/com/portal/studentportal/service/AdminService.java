package com.portal.studentportal.service;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService {
    private final StudentRepository studentRepository;

    public AdminService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents() {
       return studentRepository.findAll();
    }
    // You can add more admin methods here later (deleteStudent, updateRole, etc.)
}
