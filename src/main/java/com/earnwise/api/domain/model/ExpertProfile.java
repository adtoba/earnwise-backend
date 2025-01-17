package com.earnwise.api.domain.model;

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

@Entity
@Data
public class ExpertProfile {
    @Id
    @UuidGenerator
    private String id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String fullName;
    private String coverImage;
    private String userId;
    private String category;
    private Integer totalReviews = 0;
    private Integer totalRatings = 0;
    private Double ratings = 0.0;
    @ElementCollection
    private List<String> topics = new ArrayList<>();
    private Double hourlyRate;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
