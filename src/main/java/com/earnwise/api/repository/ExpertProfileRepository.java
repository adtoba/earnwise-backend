package com.earnwise.api.repository;

import com.earnwise.api.domain.model.ExpertProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpertProfileRepository extends JpaRepository<ExpertProfile, String> {
    Optional<ExpertProfile> findByCategory(String category);
    Optional<ExpertProfile> findByUserId(String userId);

    List<ExpertProfile> findAllByCategory(String category);

    @Query("SELECT e FROM ExpertProfile e WHERE EXISTS (SELECT s FROM e.topics s WHERE s IN :topics)")
    List<ExpertProfile> findByTopics(List<String> topics);
}
