package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.DeliveryDTO;
import com.yassine.ecommerceapi.Dto.ProductDTO;
import com.yassine.ecommerceapi.Dto.ProductRequest;
import com.yassine.ecommerceapi.Dto.ReviewDTO;
import com.yassine.ecommerceapi.Entity.Category;
import com.yassine.ecommerceapi.Entity.Product;
import com.yassine.ecommerceapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductMapper {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    static ReviewMapper reviewMapper;
    // Product → ProductDto
    public static ProductDTO toDto(Product product) {

      /*  List<ReviewDTO> reviewDTOs = product.getReviews() != null ?
                product.getReviews().stream()
                        .filter(Objects::nonNull)
                        .map(reviewMapper::reviewToReviewDTO)
                        .collect(Collectors.toList()) :
                List.of();*/

        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .quantity(product.getQuantity())
             //   .reviewDTOS(reviewDTOs)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }

    // ProductDto → Product
    public static Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .quantity(request.getQuantity())
                .build();
    }

    public static Product toEntity(ProductRequest request, Category category) {
        Product product = toEntity(request);
        product.setCategory(category);
        return product;
    }


}
