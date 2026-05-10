package com.portal.studentportal.repository;

import com.portal.studentportal.dtos.AdminDto;
import com.portal.studentportal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUsername(String username);
    AdminDto findStudent();
}
