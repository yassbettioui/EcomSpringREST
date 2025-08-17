package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.CartDTO;
import com.yassine.ecommerceapi.Dto.CartItemDTO;
import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Entity.*;
import com.yassine.ecommerceapi.enums.OrderStatus;
import com.yassine.ecommerceapi.mapper.CartItemMapper;
import com.yassine.ecommerceapi.mapper.CartMapper;
import com.yassine.ecommerceapi.mapper.OrderMapper;
import com.yassine.ecommerceapi.repository.CartRepository;
import com.yassine.ecommerceapi.repository.OrderRepository;
import com.yassine.ecommerceapi.repository.ProductRepository;
import com.yassine.ecommerceapi.repository.UserRepository;
import com.yassine.ecommerceapi.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemMapper cartItemMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public CartDTO addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            item.setTotalPrice(item.getQuantity() * item.getUnitPrice());
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setUnitPrice(product.getPrice());
            item.setTotalPrice(quantity * product.getPrice());
            cart.getItems().add(item);
        }

        // Recalculer le total du panier
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cart.setTotal(total);

        Cart savedCart = cartRepository.save(cart);

        return cartMapper.cartToCartDTO(savedCart);
    }

    @Override
    public CartDTO getCartByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        return cartMapper.cartToCartDTO(cart);
    }

    @Override
    public void removeItemFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser(
                userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"))
        ).orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        cart.getItems().removeIf(item -> item.getProductId().equals(productId));

        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cart.setTotal(total);

        cartRepository.save(cart);
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUser(
                userRepository.findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"))
        ).orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        cart.getItems().clear();
        cart.setTotal(0);

        cartRepository.save(cart);
    }

    @Override
    public CartItemDTO updateCartItemQuantity(Long userId, Long productId, int newQuantity) {
        if (newQuantity < 1) {
            throw new IllegalArgumentException("La quantité doit être au moins 1");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable dans le panier"));

        // Vérifier le stock disponible si nécessaire
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

        if (newQuantity > product.getQuantity()) {
            throw new IllegalArgumentException("Quantité demandée supérieure au stock disponible");
        }

        item.setQuantity(newQuantity);
        item.setTotalPrice(newQuantity * item.getUnitPrice());

        // Recalculer le total du panier
        double total = cart.getItems().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
        cart.setTotal(total);

        Cart savedCart = cartRepository.save(cart);

        return cartItemMapper.toDTO(item);
    }

    @Override
    public OrderDTO checkout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Impossible de passer commande avec un panier vide");
        }

        // Vérifier le stock pour tous les articles
        for (CartItem item : cart.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Stock insuffisant pour le produit: " + product.getName());
            }
        }

        // Créer la commande
        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        // Convertir les CartItems en OrderItems
        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(productRepository.findById(cartItem.getProductId()).get());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setPrice(cartItem.getTotalPrice());
            order.getItems().add(orderItem);

            // Mettre à jour le stock
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productRepository.save(product);
        }

        order.setTotalAmount(cart.getTotal());
        Order savedOrder = orderRepository.save(order);

        // Vider le panier
        cart.getItems().clear();
        cart.setTotal(0);
        cartRepository.save(cart);

        return orderMapper.orderToOrderDTO(savedOrder);
    }
}
