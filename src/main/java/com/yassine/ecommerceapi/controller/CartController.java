package com.yassine.ecommerceapi.controller;

import com.yassine.ecommerceapi.Dto.CartDTO;
import com.yassine.ecommerceapi.Dto.CartItemDTO;
import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Ajouter un produit au panier
    @PostMapping("/add")
    public CartDTO addToCart(@RequestParam Long userId,
                             @RequestParam Long productId,
                             @RequestParam int quantity) {
        return cartService.addToCart(userId, productId, quantity);
    }

    // Récupérer le panier de l'utilisateur
    @GetMapping("/{userId}")
    public CartDTO getCart(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    // Supprimer un produit du panier
    @DeleteMapping("/remove")
    public void removeItem(@RequestParam Long userId,
                           @RequestParam Long productId) {
        cartService.removeItemFromCart(userId, productId);
    }

    // Vider complètement le panier
    @DeleteMapping("/clear/{userId}")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }


    @PutMapping("/update")
    public CartItemDTO updateQuantity(@RequestParam Long userId,
                                      @RequestParam Long productId,
                                      @RequestParam int newQuantity) {
        return cartService.updateCartItemQuantity(userId, productId, newQuantity);
    }

    @PostMapping("/checkout")
    public OrderDTO checkout(@RequestParam Long userId) {
        return cartService.checkout(userId);
    }
}
