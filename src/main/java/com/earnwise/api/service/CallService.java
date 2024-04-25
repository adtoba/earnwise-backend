package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.dto.DeclineCallRequest;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.repository.CallRepository;
import com.earnwise.api.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Transactional
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
        call.setSuggestedTimes(createCallRequest.getSuggestedTimes());
        call.setUserId(userId);
        return callRepository.save(call);
    }

    @Transactional
    public void acceptCall(String id) {
        Optional<Call> call = callRepository.findById(id);
        if (call.isEmpty()) {
            throw new NotFoundException("Call not found");
        }

        Optional<UserProfile> profile = userProfileRepository.findByUserId(call.get().getUserId());
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        Call updatedCall = call.get();
        updatedCall.setStatus("accepted");
        updatedCall.setAcceptedTime(LocalDateTime.now().toString());

        callRepository.save(updatedCall);
    }

    @Transactional
    public void declineCall(String id, DeclineCallRequest declineCallRequest) {
        Optional<Call> call = callRepository.findById(id);
        if (call.isEmpty()) {
            throw new NotFoundException("Call not found");
        }

        Optional<UserProfile> profile = userProfileRepository.findByUserId(call.get().getUserId());
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        Call updatedCall = call.get();
        updatedCall.setStatus("declined");
        updatedCall.setCancelReason(declineCallRequest.getCancelReason());

        callRepository.save(updatedCall);
    }

    public List<Call> getUserCalls(String userId) {
        return callRepository.findAllByUserId(userId);
    }
}
