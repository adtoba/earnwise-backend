package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateExpertProfileRequest;
import com.earnwise.api.domain.exception.BadRequestException;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.model.ExpertProfile;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.repository.ExpertProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpertProfileService {
    private final ExpertProfileRepository expertProfileRepository;

    public ExpertProfileService(ExpertProfileRepository expertProfileRepository) {
        this.expertProfileRepository = expertProfileRepository;
    }

    @Transactional
    public ExpertProfile create(CreateExpertProfileRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        Optional<ExpertProfile> profileOptional = expertProfileRepository
                .findByUserId(user.getId());

        if(profileOptional.isPresent()) {
            throw new BadRequestException("Expert profile already exists");
        }

        ExpertProfile expertProfile = getExpertProfile(request, user);

        return expertProfileRepository.save(expertProfile);
    }

    private static ExpertProfile getExpertProfile(CreateExpertProfileRequest request, User user) {
        ExpertProfile expertProfile = new ExpertProfile();
        expertProfile.setTitle(request.getTitle());
        expertProfile.setDescription(request.getDescription());
        expertProfile.setCategory(request.getCategory());
        expertProfile.setUserId(user.getId());
        expertProfile.setFullName(user.getFullName());
        expertProfile.setTopics(request.getTopics());
        expertProfile.setCoverImage(request.getCoverImage());
        expertProfile.setHourlyRate(request.getHourlyRate());
        return expertProfile;
    }

    public ExpertProfile getExpertProfileById(String id) {
        Optional<ExpertProfile> expertProfileOptional = expertProfileRepository.findById(id);
        if(expertProfileOptional.isEmpty()) {
            throw new NotFoundException("Expert profile not found");
        }
        return expertProfileOptional.get();
    }

    public ExpertProfile getExpertProfileByUserId(String userId) {
        Optional<ExpertProfile> expertProfileOptional = expertProfileRepository.findByUserId(userId);
        if(expertProfileOptional.isEmpty()) {
            throw new NotFoundException("Expert profile not found here");
        }
        return expertProfileOptional.get();
    }

    public Optional<ExpertProfile> getOptionalExpertProfileByUserId(String userId) {
        return  expertProfileRepository.findByUserId(userId);    
    }

    public List<ExpertProfile> getAllExpertProfileByCategory(String category) {
        return expertProfileRepository.findAllByCategory(category);
    }

    public List<ExpertProfile> getAllExpertProfileByTopics(List<String> topics) {
        return expertProfileRepository.findByTopics(topics);
    }

    public List<ExpertProfile> getAllExperts() {
        return expertProfileRepository.findAll();
    }
}
