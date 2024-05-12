package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.dto.DeclineCallRequest;
import com.earnwise.api.domain.exception.BadRequestException;
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

    public Call createCall(String userId, CreateCallRequest createCallRequest) {
        Optional<UserProfile> profile = userProfileRepository.findByUserId(userId);
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        Call call = new Call();
        call.setReason(createCallRequest.getReason());
        call.setUserName(profile.get().getFullName());
        call.setUserId(profile.get().getId());
        call.setExpertName(createCallRequest.getExpertName());
        call.setExpertId(createCallRequest.getExpertId());
        call.setExpertProfilePic(createCallRequest.getExpertProfilePic());
        call.setPaid(false);
        call.setHourlyRate(createCallRequest.getHourlyRate());
        call.setTotalMinutes(createCallRequest.getTotalMinutes());
        call.setUserProfilePic(profile.get().getProfilePic());
        call.setStatus("pending");
        call.setSuggestedTimes(createCallRequest.getSuggestedTimes());
        call.setUserId(userId);


        return callRepository.save(call);
    }

    @Transactional
    public void acceptCall(String id) {
        Call call = getCallById(id);

        if(call.getStatus().equals("accepted")) {
            throw new BadRequestException("Call has already been accepted");
        }

        Optional<UserProfile> profile = userProfileRepository.findByUserId(call.getUserId());
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }

        call.setStatus("accepted");
        call.setAcceptedTime(LocalDateTime.now().toString());
    }

    @Transactional
    public void declineCall(String id, DeclineCallRequest declineCallRequest) {
        Call call = getCallById(id);

        if(call.getStatus().equals("declined")) {
            throw new BadRequestException("Call has already been declined");
        }

        Optional<UserProfile> profile = userProfileRepository.findByUserId(call.getUserId());
        if (profile.isEmpty()) {
            throw new NotFoundException("User profile not found");
        }
        call.setStatus("declined");
        call.setCancelReason(declineCallRequest.getCancelReason());
    }

    public Call getCallById(String id) {
        Optional<Call> result = callRepository.findById(id);
        if(result.isEmpty()) {
            throw new NotFoundException("Call not found");
        }

        return result.get();
    }


    public List<Call> getSentRequestCalls(String userId) {
        return callRepository.findAllByStatusAndUserId("pending", userId);
    }

    public List<Call> getActiveCalls(String userId) {
        return callRepository.findAllByStatusAndUserId("accepted", userId);
    }

    public List<Call> getPastCalls(String userId) {
        return callRepository.findAllByStatusAndUserId("completed", userId);
    }

    public List<Call> getRequestCalls(String userId) {
        return callRepository.findAllByStatusAndUserId("completed", userId);
    }

    public List<Call> getUserCalls(String userId) {
        return callRepository.findAllByUserId(userId);
    }
}
