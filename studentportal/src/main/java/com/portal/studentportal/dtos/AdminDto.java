package com.portal.studentportal.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDto {
    private Long id;
    private String username;
    private String email;
    private String roll_no;
    private String role;
}
