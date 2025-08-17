package com.yassine.ecommerceapi.Entity;

import com.yassine.ecommerceapi.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderDate;


    private double totalAmount;

    public BigDecimal getTotalAmount() {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(BigDecimal.valueOf(item.getPrice()));
        }
        return total;
    }

    public void updateStatus(OrderStatus newStatus) {
        // Validation de la transition d'état
        if (!this.status.canTransitionTo(newStatus)) {
            throw new IllegalStateException("Transition non autorisée");
        }
        this.status = newStatus;
    }

    public boolean canBeCancelled() {
        return this.status == OrderStatus.PENDING
                || this.status == OrderStatus.CANCELLED;
    }
}
