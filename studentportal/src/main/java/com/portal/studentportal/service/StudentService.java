package com.portal.studentportal.service;

import com.portal.studentportal.dtos.AuthResponse;
import com.portal.studentportal.dtos.LoginRequest;
import com.portal.studentportal.dtos.RegisterRequest;
import com.portal.studentportal.entity.Student;
import com.portal.studentportal.repository.StudentRepository;
import com.portal.studentportal.security.JWTUtil;
import com.portal.studentportal.security.JwtAuthFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final StudentRepository studentRepository;
    private final AuthenticationManager authManager;
    private final JWTUtil jwtService;
    public StudentService(StudentRepository studentRepository, AuthenticationManager authManager, JWTUtil jwtService) {
        this.studentRepository = studentRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }
    public List<Student> findAll() {
        return studentRepository.findAll();
    }


    // method to register students
    public Student registerStudent(Student student) {
        // to encrypt passwords
        student.setPassword(encoder.encode(student.getPassword()));
        // students registering themselves are always assigned USER role
        student.setRole("USER");
        return studentRepository.save(student);
    }
    public String verify(LoginRequest student){
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(student.getUsername(), student.getPassword()));
        if(authentication.isAuthenticated()){
            // returns true or false
            return jwtService.generateToken(student.getUsername());
        }
        else{
            return "Fail";
        }
    }
    public AuthResponse registerAndGetToken(RegisterRequest req) {
        Student student = new Student(req.getRoll_no(), req.getEmail(), req.getUsername(), req.getPassword(), "USER");
        Student saved = registerStudent(student);
        String token = jwtService.generateToken(saved.getUsername());
        return new AuthResponse(token);
    }

}
