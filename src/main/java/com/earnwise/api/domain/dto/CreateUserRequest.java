package com.earnwise.api.domain.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreateUserRequest {
    private String fullName;
    private String email;
    private String password;
}
