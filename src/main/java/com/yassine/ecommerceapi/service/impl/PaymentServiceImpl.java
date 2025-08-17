package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.PaymentDTO;
import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.Entity.Payment;
import com.yassine.ecommerceapi.enums.OrderStatus;
import com.yassine.ecommerceapi.enums.PaymentMethod;
import com.yassine.ecommerceapi.enums.PaymentStatus;
import com.yassine.ecommerceapi.mapper.PaymentMapper;
import com.yassine.ecommerceapi.repository.OrderRepository;
import com.yassine.ecommerceapi.repository.PaymentRepository;
import com.yassine.ecommerceapi.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public PaymentDTO processPayment(Long orderId, String paymentMethod, double amount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable"));

        // Vérifier qu'il n'y a pas déjà un paiement pour cette commande
        if (order.getPayment() != null) {
            throw new IllegalStateException("Cette commande a déjà été payée");
        }

        Payment payment = Payment.builder()
                .order(order)
                .amount(amount)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .status(PaymentStatus.PENDING)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        order.setPayment(savedPayment);
        orderRepository.save(order);

        return paymentMapper.toDto(savedPayment);
    }

    @Override
    public PaymentDTO getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));
        return paymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByOrder(Long orderId) {
        Optional<Payment> payments = paymentRepository.findByOrderId(orderId);
        return payments.stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }
}