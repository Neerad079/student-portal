package com.portal.studentportal.controllers;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/students")
    public List<Student> getAllStudents() {
       return adminService.getAllStudents();
    }
}
