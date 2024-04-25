package com.earnwise.api.service;

import com.earnwise.api.domain.dto.UpdateProfileRequest;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.mapper.UserProfileViewMapper;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.repository.UserProfileRepository;
import com.earnwise.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService {

    private final UserProfileViewMapper userProfileViewMapper;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public UserProfileService(UserProfileViewMapper userProfileViewMapper, UserProfileRepository userProfileRepository,
                              UserRepository userRepository) {
        this.userProfileViewMapper = userProfileViewMapper;
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(String email) {
        Optional<User> user = userRepository.findByUsername(email);

        if(user.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        UserProfile userProfile = new UserProfile();
        userProfile.setFullName(user.get().getFullName());
        userProfile.setUsername(user.get().getUsername());
        userProfile.setEmail(user.get().getUsername());
        userProfile.setUserId(user.get().getId());
        userProfile.setRating(0.0);
        userProfile.setTotalCalls(0);
        userProfile.setTotalRatings(0);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public UserProfileView getUserProfile(String userId) {
        Optional<UserProfile> profile = userProfileRepository.findByUserId(userId);
        if(profile.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return userProfileViewMapper.toUserProfileView(profile.get());
    }

    @Transactional
    public void updateProfile(String userId, UpdateProfileRequest updateProfileRequest) {
        Optional<UserProfile> profile = userProfileRepository.findByUserId(userId);
        if(profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        UserProfile userProfile = profile.get();
        userProfile.setBio(updateProfileRequest.getBio());
        userProfile.setTimezone(updateProfileRequest.getTimezone());
        userProfile.setLocation(updateProfileRequest.getLocation());
        userProfile.setPhoneNumber(updateProfileRequest.getPhoneNumber());
        userProfile.setProfessionalTitle(updateProfileRequest.getProfessionalTitle());

        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void updateProfilePic(String userId, String profileUrl) {
        Optional<UserProfile> profile = userProfileRepository.findByUserId(userId);
        if(profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        UserProfile userProfile = profile.get();
        userProfile.setProfilePic(profileUrl);
        userProfileRepository.save(userProfile);
    }
}
