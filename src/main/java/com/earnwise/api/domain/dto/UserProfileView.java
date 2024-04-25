package com.earnwise.api.domain.dto;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class UserProfileView {
    private String id;
    private String fullName;
    private String username;
    private String profilePic;
    private boolean isVerified;
    private String professionalTitle;
    private String email;
    private String bio;
    private String location;
    private String timezone;
    private String phoneNumber;
    private Double rating;
    private Integer totalRatings;
    private Integer totalCalls;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
