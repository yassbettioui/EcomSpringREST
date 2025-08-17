package com.yassine.ecommerceapi.controller;


import com.yassine.ecommerceapi.Dto.PaymentDTO;
import com.yassine.ecommerceapi.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> processPayment(
            @RequestParam Long orderId,
            @RequestParam String paymentMethod,
            @RequestParam double amount) {
        PaymentDTO paymentDTO = paymentService.processPayment(orderId, paymentMethod, amount);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long id) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);
        return ResponseEntity.ok(paymentDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDTO>> getPaymentsByOrder(@PathVariable Long orderId) {
        List<PaymentDTO> payments = paymentService.getPaymentsByOrder(orderId);
        return ResponseEntity.ok(payments);
    }
}