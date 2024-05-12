package com.earnwise.api.domain.dto;

import lombok.Data;

@Data
public class CreateSocialRequest {
    private String userId;
    private String instagram;
    private String twitter;
    private String linkedIn;
}


