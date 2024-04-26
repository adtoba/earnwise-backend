package com.earnwise.api.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateExpertProfileRequest {
    private String title;
    private String description;
    private String coverImage;
    private String category;
    private String userId;
    private List<String> topics;
    private Double hourlyRate;
}
