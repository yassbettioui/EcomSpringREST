package com.yassine.ecommerceapi.service;


import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Dto.OrderItemDTO;
import com.yassine.ecommerceapi.Dto.PaymentDTO;
import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    /**
     * Crée une nouvelle commande.
     *
     * @param orderDTO Les informations de la commande.
     * @return L'objet OrderDTO représentant la commande créée.
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * Récupère toutes les commandes d'un client.
     *
     * @param customerId L'ID du client.
     * @return Une liste de DTOs représentant les commandes du client.
     */
    List<OrderDTO> getOrdersByCustomer(Long customerId);

    /**
     * Traite un paiement pour une commande.
     *
     * @param orderId L'ID de la commande.
     * @param paymentDTO Les informations de paiement.
     * @return Le DTO représentant le paiement traité.
     */
    PaymentDTO processPayment(Long orderId, PaymentDTO paymentDTO);
    OrderDTO getOrderById(Long id);

    List<OrderDTO> getAllOrders();

    void deleteOrder(Long id);

    Order updateOrderStatus(Long id, OrderStatus status);

    List<OrderItemDTO> getOrderItems(Long id);

    OrderDTO confirmCartAsOrder(Long userId);
}
