package com.earnwise.api.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PushNotification {
    private Map<String, Object> include_aliases;
    private String target_channel;
    private Map<String, Object> contents;
    private Map<String, Object> headings;
    private String app_id;
    private boolean isIos;
    private boolean isAndroid;
    private String existing_android_channel_id;
}
