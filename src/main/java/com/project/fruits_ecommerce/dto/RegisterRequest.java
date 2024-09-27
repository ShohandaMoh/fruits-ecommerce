package com.project.fruits_ecommerce.dto;

import com.project.fruits_ecommerce.entities.RoleName;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private RoleName roleName;
}