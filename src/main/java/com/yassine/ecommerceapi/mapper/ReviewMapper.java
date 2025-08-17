package com.yassine.ecommerceapi.mapper;


import com.yassine.ecommerceapi.Dto.ReviewDTO;
import com.yassine.ecommerceapi.Entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "user.id", target = "userId")
    ReviewDTO reviewToReviewDTO(Review review);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "userId", target = "user.id")
    Review reviewDTOToReview(ReviewDTO reviewDTO);
}
