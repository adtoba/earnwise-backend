package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateSocialRequest;
import com.earnwise.api.domain.dto.SocialsView;
import com.earnwise.api.domain.dto.UpdateSocialsRequest;
import com.earnwise.api.domain.exception.BadRequestException;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.mapper.SocialsViewMapper;
import com.earnwise.api.domain.model.Socials;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.repository.SocialsRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialsService {

    private final SocialsRepository socialsRepository;
    private final SocialsViewMapper socialsViewMapper;

    public SocialsService(SocialsRepository socialsRepository, SocialsViewMapper socialsViewMapper) {
        this.socialsRepository = socialsRepository;
        this.socialsViewMapper = socialsViewMapper;
    }

    @Transactional
    public SocialsView create(CreateSocialRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Socials> optionalSocials = socialsRepository.findByUserId(user.getId());
        if (optionalSocials.isPresent()) {
            throw new BadRequestException("Social already exists");
        }

        Socials socials = new Socials();
        socials.setInstagram(request.getInstagram());
        socials.setTwitter(request.getTwitter());
        socials.setLinkedIn(request.getLinkedIn());

        Socials result = socialsRepository.save(socials);
        return socialsViewMapper.toSocialsView(result);
    }

    @Transactional
    public void update(UpdateSocialsRequest request) {
        SocialsView socials = getSocialsById(request.getId());

        socials.setInstagram(request.getInstagram());
        socials.setTwitter(request.getTwitter());
        socials.setLinkedIn(request.getLinkedIn());
    }

    public SocialsView getSocialsByUserId(String userId) {
        Optional<Socials> result = socialsRepository.findByUserId(userId);
        if (result.isEmpty()) {
            throw new NotFoundException("Social not found");
        }

        return socialsViewMapper.toSocialsView(result.get());
    }

    public SocialsView getSocialsById(String id) {
        Optional<Socials> result = socialsRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Social not found");
        }

        return socialsViewMapper.toSocialsView(result.get());
    }

}
