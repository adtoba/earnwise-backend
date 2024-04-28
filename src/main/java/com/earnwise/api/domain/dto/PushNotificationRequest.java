package com.earnwise.api.domain.dto;

import lombok.Data;

@Data
public class PushNotificationRequest {
    private String title;
    private String desc;
    private String userId;
}
