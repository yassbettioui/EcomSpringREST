package com.yassine.ecommerceapi.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    private Long productId;

    private int quantity;

    private double unitPrice;

    private double totalPrice;

}
