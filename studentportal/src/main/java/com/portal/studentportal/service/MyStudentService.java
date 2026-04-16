package com.portal.studentportal.service;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.entity.UserPrincipal;
import com.portal.studentportal.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyStudentService implements UserDetailsService {
    private final StudentRepository studentRepository;

    public MyStudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username);
        if (student == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(student);
    }
}
