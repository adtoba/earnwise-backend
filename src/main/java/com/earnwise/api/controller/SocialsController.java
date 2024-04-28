package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.SocialsView;
import com.earnwise.api.domain.dto.UpdateSocialsRequest;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.SocialsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/socials")
public class SocialsController {
    private final SocialsService socialsService;

    public SocialsController(SocialsService socialsService) {
        this.socialsService = socialsService;
    }

    @GetMapping
    public ResponseEntity<?> getUserSocials() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SocialsView result = socialsService.getSocialsByUserId(user.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("{user_id}")
    public ResponseEntity<?> getSocialsByUserId(@PathVariable String user_id) {
        SocialsView result = socialsService.getSocialsByUserId(user_id);
        return ResponseEntity.ok(result);
    }

    @PutMapping
    public ResponseEntity<?> updateUserSocials(@RequestBody UpdateSocialsRequest request) {
        socialsService.update(request);
        Map<String, Object> result = new HashMap<>();
        result.put("status", true);
        result.put("message", "Socials updated successfully");
        return ResponseEntity.ok(result);
    }
}
