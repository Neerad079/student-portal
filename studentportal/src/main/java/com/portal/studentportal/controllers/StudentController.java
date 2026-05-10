package com.portal.studentportal.controllers;

import com.portal.studentportal.dtos.DashboardDto;
import com.portal.studentportal.dtos.RegisterRequest;
import com.portal.studentportal.entity.Student;
import com.portal.studentportal.repository.StudentRepository;
import com.portal.studentportal.service.StudentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController( StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.findAll();
    };
    @GetMapping("/me")
    public DashboardDto getStudentProfile(Principal principal ) {
        // principal.getName() gives you the username  from JWT
        return studentService.getStudentByUsername(principal.getName());
    }

    }
