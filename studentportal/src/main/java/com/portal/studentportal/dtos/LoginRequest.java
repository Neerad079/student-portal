package com.portal.studentportal.dtos;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.service.StudentService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@Data
public class LoginRequest {
   private String username;
   private String password;
}

