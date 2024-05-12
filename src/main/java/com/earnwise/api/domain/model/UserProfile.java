package com.earnwise.api.domain.model;

import com.earnwise.api.domain.dto.ExpertProfileView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;


import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity(name = "user_profile")
@Data
public class UserProfile implements Serializable {
    @Id
    @UuidGenerator
    private String id;

    private String fullName;
    private String username;
    private String profilePic;
    private String userId;
    private String professionalTitle;
    private String email;
    private String bio;
    private String location;
    private String timezone;
    private String phoneNumber;
    private Double rating;
    private Integer totalRatings;
    private Integer totalCalls;

    private boolean isVerified;

    @ElementCollection
    private List<String> interests = new ArrayList<>();
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
