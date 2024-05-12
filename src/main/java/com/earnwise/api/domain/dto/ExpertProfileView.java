package com.earnwise.api.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExpertProfileView {
    private String id;

    private String title;
    private String description;
    private String fullName;
    private String coverImage;
    private String userId;
    private String category;
    private Integer totalReviews = 0;
    private Integer totalRatings = 0;
    private Double ratings = 0.0;
    private List<String> topics = new ArrayList<>();
    private Double hourlyRate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
