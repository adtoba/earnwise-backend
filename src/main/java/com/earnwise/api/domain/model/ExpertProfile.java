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
    private String description;
    private String coverImage;
    private String userId;
    private String category;
    @ElementCollection
    private List<String> topics = new ArrayList<>();
    private Double hourlyRate;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
