package com.earnwise.api.domain.dto;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private String review;
    private double rating = 0;
    private String expertId;
    private String expertProfilePic;
}
