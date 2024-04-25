package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.UpdateProfileRequest;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.service.UserProfileService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/profile")
public class UserProfileController {

    private UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public UserProfileView getUserProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return userProfileService.getUserProfile(user.getId());
    }

    @PutMapping
    public void updateUserProfile(@RequestBody UpdateProfileRequest updateProfileRequest) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        userProfileService.updateProfile(user.getId(), updateProfileRequest);
    }

}
