package com.yassine.ecommerceapi.Dto;

import lombok.*;
@Builder
@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long orderId;
    private String productName;
    private Long productId;
    private Integer quantity;
    private double unitPrice;
    private double price;
}