package com.yassine.ecommerceapi.service;

import com.yassine.ecommerceapi.Dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    //PaymentDTO processPayment(Long orderId, PaymentDTO paymentDTO);

    PaymentDTO processPayment(Long orderId, String paymentMethod, double amount);

    PaymentDTO getPaymentById(Long id);

    List<PaymentDTO> getPaymentsByOrder(Long orderId);
}

