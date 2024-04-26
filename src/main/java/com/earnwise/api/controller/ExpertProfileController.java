package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.CreateExpertProfileRequest;
import com.earnwise.api.domain.dto.GetExpertRequest;
import com.earnwise.api.domain.model.ExpertProfile;
import com.earnwise.api.service.ExpertProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/expert")
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;

    public ExpertProfileController(ExpertProfileService expertProfileService) {
        this.expertProfileService = expertProfileService;
    }

    @PostMapping
    public ResponseEntity<?> createExpertProfile(@RequestBody CreateExpertProfileRequest request) {
        ExpertProfile expertProfile = expertProfileService.create(request);
        return ResponseEntity.ok(expertProfile);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getExpertProfile(@PathVariable String id) {
        ExpertProfile expertProfile = expertProfileService.getExpertProfileById(id);
        return ResponseEntity.ok(expertProfile);
    }

    @GetMapping
    public ResponseEntity<?> getExpertProfileByUserId(@RequestParam("userId") String userId) {
        ExpertProfile expertProfile = expertProfileService.getExpertProfileByUserId(userId);
        return ResponseEntity.ok(expertProfile);
    }

    @GetMapping("category/{category}")
    public ResponseEntity<?> getExpertProfileByCategory(@PathVariable String category) {
        ExpertProfile expertProfile = expertProfileService.getExpertProfileByCategory(category);
        return ResponseEntity.ok(expertProfile);
    }

    @PostMapping("topics")
    public ResponseEntity<?> getExpertByTopics(@RequestBody GetExpertRequest request) {
        List<ExpertProfile> expertProfiles = expertProfileService.getExpertProfileByTopics(request.getTopics());
        return ResponseEntity.ok(expertProfiles);
    }

}
