package com.earnwise.api.service;

import com.earnwise.api.domain.dto.PushNotification;
import com.earnwise.api.domain.dto.PushNotificationRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {
    private final WebClient webClient;

    public NotificationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.onesignal.com")
                .defaultHeader("Authorization", "Njk1ZmI0ZWQtMDYwNS00M2Q5LTg0ZjgtZmRkYzRmNzcxMjY1")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }

    public void sendPushNotification(PushNotificationRequest request) {
        PushNotification pushNotification = getPushNotification(request);
        System.out.println("NOTIFICATION SENT");
        webClient.post()
                .uri("/notifications")
                .body(Mono.just(pushNotification), PushNotification.class);
    }

    private static PushNotification getPushNotification(PushNotificationRequest request) {
        Map<String, Object> content = new HashMap<>();
        content.put("en", request.getTitle());

        Map<String, Object> headings = new HashMap<>();
        content.put("en", request.getTitle());

        Map<String, Object> aliases = new HashMap<>();
        aliases.put("external_id", List.of(request.getUserId()));

        PushNotification pushNotification = new PushNotification();
        pushNotification.setInclude_aliases(aliases);
        pushNotification.setContents(content);
        pushNotification.setHeadings(headings);
        pushNotification.setApp_id("6bacf9ea-0611-4b31-aa4b-4e953d1f82e2");
        pushNotification.setTarget_channel("push");
        pushNotification.setAndroid(true);
        pushNotification.setIos(true);
        return pushNotification;
    }
}
