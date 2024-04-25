package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.repository.CallRepository;
import com.earnwise.api.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CallService {

    private final CallRepository callRepository;
    private final UserProfileRepository userProfileRepository;

    public CallService(CallRepository callRepository, UserProfileRepository userProfileRepository) {
        this.callRepository = callRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public Call createCall(String userId, CreateCallRequest createCallRequest) {
        Optional<UserProfile> profile = userProfileRepository.findByUserId(userId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        Call call = new Call();
        call.setReason(createCallRequest.getReason());
        call.setUserId(profile.get().getId());
        call.setExpertId(createCallRequest.getExpertId());
        call.setExpertProfilePic(createCallRequest.getExpertProfilePic());
        call.setPaid(false);
        call.setTotalMinutes(createCallRequest.getTotalMinutes());
        call.setUserProfilePic(profile.get().getProfilePic());
        call.setStatus("pending");
        call.setUserId(userId);
        return callRepository.save(call);
    }

    public List<Call> getUserCalls(String userId) {
        return callRepository.findAllByUserId(userId);
    }
}
