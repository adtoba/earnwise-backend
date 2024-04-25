package com.earnwise.api.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "calls")
@Data
public class Call {
    @Id
    @UuidGenerator
    private String id;

    private String reason;
    private String status;
    private String expertId;
    private String expertProfilePic;
    private String userId;
    private String userProfilePic;
    private String acceptedTime;
    private String cancelReason;
    private Integer totalMinutes;
    private boolean isPaid;

    @ElementCollection
    private List<String> suggestedTimes = new ArrayList<>();


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
