package com.earnwise.api.domain.dto;

import lombok.Data;

@Data
public class DeclineCallRequest {
    private String cancelReason;
}
