package com.earnwise.api.domain.dto;

import lombok.Data;

@Data
public class UpdateSocialsRequest {
    private String id;
    private String instagram;
    private String twitter;
    private String linkedIn;
}
