package com.yassine.ecommerceapi.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity  @Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private double price;
    private double unitPrice;


    @PrePersist
    @PreUpdate
    private void validate() {
        if (this.quantity <= 0) {
            throw new IllegalArgumentException("La quantité doit être positive");
        }
        if (this.unitPrice <= 0) {
            throw new IllegalArgumentException("Le prix unitaire doit être positif");
        }
    }
}
