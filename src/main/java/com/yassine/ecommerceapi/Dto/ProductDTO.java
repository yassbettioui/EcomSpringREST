package com.yassine.ecommerceapi.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private int quantity;
    private String categoryName;
   // private List<ReviewDTO> reviewDTOS;
}
