package com.yassine.ecommerceapi.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName; // Ajout pour l'UI
    private String productImage;
    private int quantity;
    private double unitPrice;
    private double price; // unitPrice * quantity
}

