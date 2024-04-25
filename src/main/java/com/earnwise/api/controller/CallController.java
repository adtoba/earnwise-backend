package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.CallService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CallController {

    private final CallService callService;

    public CallController(CallService callService) {
        this.callService = callService;
    }

    @PostMapping("call")
    public Call createCall() {
        return callService.createCall();
    }

    @GetMapping("call")
    public List<Call> getUserCalls() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return callService.getUserCalls(user.getId());
    }
}
