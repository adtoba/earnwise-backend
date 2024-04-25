package com.earnwise.api.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
public class CreateCallRequest {
    private String reason;
    private String expertId;
    private String expertProfilePic;
    private Integer totalMinutes;
}
