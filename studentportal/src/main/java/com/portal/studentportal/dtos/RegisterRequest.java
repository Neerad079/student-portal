package com.portal.studentportal.dtos;

import com.portal.studentportal.entity.Student;
import com.portal.studentportal.service.StudentService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Data

public class RegisterRequest {
    private String username;
    private String password;
    private String roll_no;
    private String email;
}
