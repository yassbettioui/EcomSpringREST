package com.yassine.ecommerceapi.service;


import com.yassine.ecommerceapi.Dto.CartDTO;
import com.yassine.ecommerceapi.Dto.CartItemDTO;
import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Entity.Cart;


public interface CartService {
    CartDTO addToCart(Long userId, Long productId, int quantity);
    CartDTO getCartByUserId(Long userId);
    void removeItemFromCart(Long userId, Long productId);
    void clearCart(Long userId);
    CartItemDTO updateCartItemQuantity(Long userId, Long productId, int newQuantity);
    OrderDTO checkout(Long userId);
}