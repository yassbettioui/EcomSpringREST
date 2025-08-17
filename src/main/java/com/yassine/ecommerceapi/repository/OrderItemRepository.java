package com.yassine.ecommerceapi.repository;

import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.Entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
