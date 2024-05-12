package com.earnwise.api.repository;

import com.earnwise.api.domain.model.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    List<Review> findAllByUserId(String userId);
    List<Review> findAllByExpertId(Sort sort, String expertId);
}
