package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Dto.OrderItemDTO;
import com.yassine.ecommerceapi.Dto.PaymentDTO;
import com.yassine.ecommerceapi.Entity.*;
import com.yassine.ecommerceapi.enums.OrderStatus;
import com.yassine.ecommerceapi.mapper.OrderMapper;
import com.yassine.ecommerceapi.mapper.PaymentMapper;

import com.yassine.ecommerceapi.repository.*;
import com.yassine.ecommerceapi.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final PaymentMapper paymentMapper;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository, CartRepository cartRepository, UserRepository userRepository, OrderMapper orderMapper, PaymentMapper paymentMapper) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.paymentMapper = paymentMapper;
    }

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Validation
        if (orderDTO.getItems() == null || orderDTO.getItems().isEmpty()) {
            throw new IllegalStateException("La commande ne peut pas être vide");
        }

        Order order = orderMapper.orderDTOToOrder(orderDTO);
        double total = 0;
        List<OrderItem> itemsToSave = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit introuvable"));

            // Vérification stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("Stock insuffisant pour le produit: " + product.getName());
            }

            // Mise à jour stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // Calcul prix
            item.setUnitPrice(product.getPrice());
            double itemTotal = product.getPrice() * item.getQuantity();
            item.setPrice(itemTotal);
            total += itemTotal;

            item.setOrder(order);
            itemsToSave.add(item);
        }

        order.setItems(itemsToSave);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now());

        return orderMapper.orderToOrderDTO(orderRepository.save(order));
    }



    @Override
    public List<OrderDTO> getOrdersByCustomer(Long customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(orderMapper::orderToOrderDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO processPayment(Long orderId, PaymentDTO paymentDTO) {
        // Convertir PaymentDTO en Payment
        Payment payment = paymentMapper.toEntity(paymentDTO);

        // Récupérer l'Order à partir de l'orderId
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        // Associer l'Order au Payment
        payment.setOrder(order);

        // Sauvegarder le Payment
        paymentRepository.save(payment);

        // Retourner le PaymentDTO
        return paymentMapper.toDto(payment);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).get();
        return orderMapper.orderToOrderDTO(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = orders.stream().map(o -> orderMapper.orderToOrderDTO(o)).collect(Collectors.toList());
return orderDTOS;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable avec l'id : " + id));

        order.setStatus(status);
        return orderRepository.save(order);
    }


    @Override
    public List<OrderItemDTO> getOrderItems(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable avec l'id : " + id));

        return order.getItems().stream()
                .map(orderItem -> OrderItemDTO.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProduct().getId())
                        .productName(orderItem.getProduct().getName())
                        .quantity(orderItem.getQuantity())
                        .unitPrice(orderItem.getUnitPrice())
                        .price(orderItem.getPrice())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public OrderDTO confirmCartAsOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Panier introuvable"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("Le panier est vide");
        }

        Order order = new Order();
        order.setCustomer(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING); // Enum ou String selon ton modèle
        order.setTotalAmount(cart.getTotal());

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            // Récupérer l'entité Product par son ID
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id " + cartItem.getProductId()));

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setUnitPrice(cartItem.getUnitPrice());
            orderItem.setPrice(cartItem.getTotalPrice());

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        orderRepository.save(order);

        // Optionnel : vider le panier
        cart.getItems().clear();
        cart.setTotal(0);
        cartRepository.save(cart);

        return orderMapper.orderToOrderDTO(order);
    }

}
