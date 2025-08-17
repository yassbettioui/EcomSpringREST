package com.yassine.ecommerceapi.repository;

import com.yassine.ecommerceapi.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
