package com.portal.studentportal.service;

import com.portal.studentportal.dtos.*;
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
    public String verify(LoginRequest loginRequest) {
        try {
            Authentication authentication =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                // Fetch the user from DB to get their assigned role
                Student student = studentRepository.findByUsername(loginRequest.getUsername());
                return jwtService.generateToken(student.getUsername(), student.getRole());
            }
        }catch (Exception e) {
            System.out.println("Authentication Failed: " + e.getMessage());
        }
        return "Fail";
    }
    public AuthResponse registerAndGetToken(RegisterRequest req) {
        Student student = new Student(null,req.getRoll_no(), req.getEmail(), req.getUsername(), req.getPassword(), "USER");
        Student saved = registerStudent(student);
        // saved.getRole() will always return USER but that's fine,cuz only the students will be registering themselves the admin will be created through sql itself
        String token = jwtService.generateToken(saved.getUsername(), saved.getRole());
        return new AuthResponse(token);
    }
    public DashboardDto getStudentByUsername(String username){
        Student student = studentRepository.findByUsername(username);

        return  DashboardDto.builder()
                .username(student.getUsername())
                .email(student.getEmail())
                .roll_no(student.getRoll_no())
                .build();

    }

}
