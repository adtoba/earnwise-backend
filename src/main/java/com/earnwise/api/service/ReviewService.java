package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateReviewRequest;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.exception.BadRequestException;
import com.earnwise.api.domain.model.ExpertProfile;
import com.earnwise.api.domain.model.Review;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.repository.ExpertProfileRepository;
import com.earnwise.api.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final UserProfileService userProfileService;
    private final ReviewRepository reviewRepository;
    private final ExpertProfileRepository expertProfileRepository;

    private final ExpertProfileService expertProfileService;

    public ReviewService(
            ReviewRepository reviewRepository,
            UserProfileService userProfileService,
            ExpertProfileService expertProfileService,
            ExpertProfileRepository expertProfileRepository) {
        this.reviewRepository = reviewRepository;
        this.expertProfileRepository = expertProfileRepository;
        this.expertProfileService = expertProfileService;
        this.userProfileService = userProfileService;
    }

    @Transactional
    public Review createReview(CreateReviewRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileView userProfile = userProfileService.getUserProfile(user.getId());
        ExpertProfile expertProfile = expertProfileService.getExpertProfileById(request.getExpertId());


        Review review = new Review();
        review.setReview(request.getReview());
        review.setRating(request.getRating());
        review.setExpertId(request.getExpertId());
        review.setExpertProfilePic(request.getExpertProfilePic());
        review.setUserProfilePic(userProfile.getProfilePic());
        review.setUserId(user.getId());
        review.setUserProfileId(userProfile.getId());

        double expertRatings = calculateExpertRating(expertProfile.getId());

        expertProfile.setTotalReviews(expertProfile.getTotalReviews() + 1);
        expertProfile.setTotalRatings(expertProfile.getTotalRatings() + 1);
        expertProfile.setRatings(expertRatings);

        expertProfileRepository.save(expertProfile);

        return reviewRepository.save(review);
    }

    public List<Review> getUserReviewsByExpertId(String expertId) {
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");
        return reviewRepository.findAllByExpertId(sortBy, expertId);
    }

    public List<Review> getAllReviews() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<ExpertProfile> expertProfileOptional = expertProfileRepository.findByUserId(user.getId());
        if(expertProfileOptional.isEmpty()) {
            return new ArrayList<>();
        }
        Sort sortBy = Sort.by(Sort.Direction.DESC, "createdAt");

        return reviewRepository.findAllByExpertId(sortBy, expertProfileOptional.get().getId());
    }

    public Double calculateExpertRating(String expertId) {
        List<Review> reviews = getAllReviews();
        double sum = 0;
        for(Review review : reviews) {
            sum += review.getRating();
            System.out.println(sum);
        }
        if(reviews.isEmpty()) {
            return 0.0;
        } else {
            System.out.println(sum/reviews.size());
            return sum / reviews.size();
        }
    }

}
