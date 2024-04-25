package com.earnwise.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserView {
    private String id;

    private String fullName;
    private String username;
}
