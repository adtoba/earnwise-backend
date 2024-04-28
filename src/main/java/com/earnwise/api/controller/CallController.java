package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.AcceptCallRequest;
import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.dto.DeclineCallRequest;
import com.earnwise.api.domain.dto.PushNotificationRequest;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.CallService;
import com.earnwise.api.service.NotificationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/call")
public class CallController {

    private final CallService callService;
    private final NotificationService notificationService;

    public CallController(CallService callService, NotificationService notificationService) {
        this.callService = callService;
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<?> createCall(@RequestBody CreateCallRequest createCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Call call = callService.createCall(user.getId(), createCallRequest);
        return ResponseEntity.ok(call);
    }

    @PutMapping("accept")
    public ResponseEntity<?> acceptCall(@RequestBody AcceptCallRequest acceptCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        callService.acceptCall(acceptCallRequest.getId());

        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("EarnWise");
        request.setDesc("Your call request was accepted");
        request.setUserId(user.getId());
        notificationService.sendPushNotification(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Call accepted");
        return ResponseEntity.ok(response);
    }

    @PutMapping("decline")
    public ResponseEntity<?> declineCall(@RequestBody DeclineCallRequest declineCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        callService.declineCall(declineCallRequest.getId(), declineCallRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Call declined");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Call> getUserCalls() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return callService.getUserCalls(user.getId());
    }
}
