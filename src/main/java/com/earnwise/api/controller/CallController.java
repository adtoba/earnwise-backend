package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.dto.DeclineCallRequest;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.CallService;
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

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping
    public ResponseEntity<?> createCall(@RequestBody CreateCallRequest createCallRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Call call = callService.createCall(user.getId(), createCallRequest);
        return ResponseEntity.ok(call);
    }

    @PutMapping("accept/{id}")
    public ResponseEntity<?> acceptCall(@PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        callService.acceptCall(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Call accepted");
        return ResponseEntity.ok(response);
    }

    @PutMapping("decline/{id}")
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
        return callService.getUserCalls(user.getId());
    }
}
