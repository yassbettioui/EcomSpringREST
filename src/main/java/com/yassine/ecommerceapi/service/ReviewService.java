package com.yassine.ecommerceapi.service;

import com.yassine.ecommerceapi.Dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    ReviewDTO createReview(ReviewDTO reviewDTO);
    List<ReviewDTO> getReviewsByProduct(Long productId);
    void deleteReview(Long reviewId);
    List<ReviewDTO> getAllReviews();
    ReviewDTO updateReview(Long id,ReviewDTO reviewDTO);

    ReviewDTO getReviewsById(Long id);
}

