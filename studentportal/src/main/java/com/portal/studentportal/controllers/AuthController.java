package com.portal.studentportal.controllers;

import com.portal.studentportal.dtos.AuthResponse;
import com.portal.studentportal.dtos.LoginRequest;
import com.portal.studentportal.dtos.RegisterRequest;
import com.portal.studentportal.dtos.StudentDto;
import com.portal.studentportal.entity.Student;
import com.portal.studentportal.service.StudentService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest student) {
        return studentService.registerAndGetToken(student);
    }
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        String token = studentService.verify(req);
        return new AuthResponse(token);
    }

}
