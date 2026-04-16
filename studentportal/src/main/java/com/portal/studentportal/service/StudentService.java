package com.portal.studentportal.service;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.repository.StudentRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // method to register students
    public Student registerStudent(Student student) {
        // to encrypt passwords
        student.setPassword(encoder.encode(student.getPassword()));
        // students registering themselves are always assigned USER role
        student.setRole("USER");
        return studentRepository.save(student);
    }

}
