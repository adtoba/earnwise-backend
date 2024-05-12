package com.earnwise.api.controller;

import com.earnwise.api.domain.dto.CreateReviewRequest;
import com.earnwise.api.domain.model.Review;
import com.earnwise.api.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public Review createReview(@RequestBody CreateReviewRequest request) {
        return this.reviewService.createReview(request);
    }

    @GetMapping("{expert_id}")
    public List<Review> getAllReviews(@PathVariable String expert_id) {
        return this.reviewService.getUserReviewsByExpertId(expert_id);
    }

    @GetMapping
    public List<Review> getUserReviews() {
        return this.reviewService.getAllReviews();
    }
}
