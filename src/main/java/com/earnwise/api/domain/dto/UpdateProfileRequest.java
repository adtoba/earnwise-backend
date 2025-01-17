package com.earnwise.api.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateProfileRequest {
    private String professionalTitle;
    private String location;
    private String timezone;
    private String bio;
    private String phoneNumber;
    private List<String> interests;
}
