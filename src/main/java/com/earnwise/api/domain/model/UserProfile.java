package com.earnwise.api.domain.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "user_profile")
@Data
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fullName;
    private String userName;
    private String profilePic;

    @CreatedBy
    private String userId;

    private String professionalTitle;
    private String email;
    private String bio;
    private String location;
    private String timeZone;
    private String phoneNumber;
    private Double rating;
    private Integer totalRatings;
    private Integer totalCalls;

    private boolean isVerified;

    private List<String> interests = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;



}
