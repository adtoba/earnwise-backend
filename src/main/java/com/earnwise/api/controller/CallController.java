package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.AcceptCallRequest;
import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.dto.DeclineCallRequest;
import com.earnwise.api.domain.dto.PushNotificationRequest;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.ExpertProfile;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.CallService;
import com.earnwise.api.service.ExpertProfileService;
import com.earnwise.api.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/call")
public class CallController {
    
    private final CallService callService;
    private final ExpertProfileService expertProfileService;
    private final NotificationService notificationService;

    public CallController(CallService callService, ExpertProfileService expertProfileService, NotificationService notificationService) {
        this.callService = callService;
        this.notificationService = notificationService;
        this.expertProfileService = expertProfileService;
    }

    @PostMapping
    public ResponseEntity<?> createCall(@RequestBody CreateCallRequest createCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Call call = callService.createCall(user.getId(), createCallRequest);
        return ResponseEntity.ok(call);
    }

    @PutMapping("{id}/accept")
    public ResponseEntity<?> acceptCall(@PathVariable String id, @RequestBody AcceptCallRequest acceptRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        callService.acceptCall(id, acceptRequest.getAcceptedTime());

        PushNotificationRequest request = new PushNotificationRequest();
        request.setTitle("EarnWise");
        request.setDesc("Your call request was accepted");
        request.setUserId(user.getId());
        notificationService.sendPushNotification(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Call accepted");
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/decline")
    public ResponseEntity<?> declineCall(@PathVariable String id, @RequestBody DeclineCallRequest declineCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        callService.declineCall(id, declineCallRequest);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Call declined");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<Call> getUserCalls() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<ExpertProfile> expertProfile = expertProfileService.getOptionalExpertProfileByUserId(user.getId());
        return callService.getUserCalls(user.getId(), expertProfile.get().getId());
    }

    @GetMapping("/requests")
    public List<Call> getCallRequests() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return callService.getRequestCalls(user.getId());
    }
}
