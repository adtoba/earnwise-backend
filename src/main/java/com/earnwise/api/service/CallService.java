package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateCallRequest;
import com.earnwise.api.domain.model.Call;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.repository.CallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallService {
    @Autowired
    private CallRepository callRepository;

    public Call createCall() {
        Call call = new Call();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        call.setCreatedBy(user.getId());
        return callRepository.save(call);
    }

    public List<Call> getUserCalls(String createdBy) {
        return callRepository.findAllByCreatedBy(createdBy);
    }
}
