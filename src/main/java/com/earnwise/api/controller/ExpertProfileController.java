package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.CreateExpertProfileRequest;
import com.earnwise.api.domain.dto.GetExpertRequest;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.model.ExpertProfile;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.service.ExpertProfileService;
import com.earnwise.api.service.UserProfileService;
import com.earnwise.api.utils.ObjectChecker;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/expert")
public class ExpertProfileController {

    private final ExpertProfileService expertProfileService;
    private final UserProfileService userProfileService;

    public ExpertProfileController(ExpertProfileService expertProfileService, UserProfileService userProfileService) {
        this.expertProfileService = expertProfileService;
        this.userProfileService = userProfileService;
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

    @GetMapping("all")
    public ResponseEntity<?> getAllExpertProfiles(
            @RequestParam("filter_by") String filter_by,
            @RequestParam(required = false, name = "filter_by_value") String filter_by_value
    ) throws BadRequestException {

        if(filter_by.equals("suggested")) {
            return getSuggestedExperts();
        }

        if(filter_by.equals("category")) {
            return ResponseEntity.ok(expertProfileService.getAllExpertProfileByCategory(
                    String.valueOf(filter_by_value))
            );
        }

        return ResponseEntity.ok(expertProfileService.getAllExperts());
    }


    @GetMapping()
    public ResponseEntity<?> getExpertProfileByUserId(@RequestParam("user_id") String userId) {
        ExpertProfile expertProfile = expertProfileService.getExpertProfileByUserId(userId);
        return ResponseEntity.ok(expertProfile);
    }

    @GetMapping("suggested")
    public ResponseEntity<?> getSuggestedExperts() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileView profile = userProfileService.getUserProfile(user.getId());

        List<ExpertProfile> suggestedExperts = expertProfileService.getAllExpertProfileByTopics(profile.getInterests());
        return ResponseEntity.ok(suggestedExperts);
    }
}
