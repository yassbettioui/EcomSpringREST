package com.yassine.ecommerceapi.repository;

import com.yassine.ecommerceapi.Entity.Cart;
import com.yassine.ecommerceapi.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}