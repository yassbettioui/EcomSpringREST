package com.yassine.ecommerceapi.service.impl;


import com.yassine.ecommerceapi.Dto.ReviewDTO;
import com.yassine.ecommerceapi.Entity.Product;
import com.yassine.ecommerceapi.Entity.Review;
import com.yassine.ecommerceapi.Entity.User;
import com.yassine.ecommerceapi.exception.ResourceNotFoundException;
import com.yassine.ecommerceapi.mapper.ReviewMapper;
import com.yassine.ecommerceapi.repository.ProductRepository;
import com.yassine.ecommerceapi.repository.ReviewRepository;
import com.yassine.ecommerceapi.repository.UserRepository;
import com.yassine.ecommerceapi.service.ReviewService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(
            ReviewRepository reviewRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            ReviewMapper reviewMapper
    ) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.reviewMapper = reviewMapper;
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Charger le produit
        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id: " + reviewDTO.getProductId()));

        // Charger l'utilisateur
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé avec id: " + reviewDTO.getUserId()));

        // Mapper DTO vers entité
        Review review = reviewMapper.reviewDTOToReview(reviewDTO);

        // Attacher les vraies entités
        review.setProduct(product);
        review.setUser(user);

        // Sauvegarder
        Review savedReview = reviewRepository.save(review);

        // Retourner DTO
        return reviewMapper.reviewToReviewDTO(savedReview);
    }

    @Override
    public List<ReviewDTO> getReviewsByProduct(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(reviewMapper::reviewToReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(reviewMapper::reviewToReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));

        // Mettre à jour les champs
        existingReview.setRating(reviewDTO.getRating());
        existingReview.setComment(reviewDTO.getComment());
        existingReview.setCreatedAt(reviewDTO.getCreatedAt());

        // Si tu veux aussi mettre à jour le user ou product, tu peux le faire ici
        // (normalement on évite de changer le user/product sur une review existante, mais tu peux le permettre si tu veux)

        Review updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.reviewToReviewDTO(updatedReview);
    }

    @Override
    public ReviewDTO getReviewsById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with id: " + id));
        return reviewMapper.reviewToReviewDTO(review);
    }

}


