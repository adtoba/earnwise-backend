package com.earnwise.api.repository;

import com.earnwise.api.domain.model.Socials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialsRepository extends JpaRepository<Socials, String> {
    Optional<Socials> findByUserId(String userId);
}
