package com.yassine.ecommerceapi.controller;

import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Dto.OrderItemDTO;
import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.enums.OrderStatus;
import com.yassine.ecommerceapi.mapper.OrderMapper;
import com.yassine.ecommerceapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersByCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        Order order = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(orderMapper.orderToOrderDTO(order));
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<OrderItemDTO>> getOrderItems(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderItems(id));
    }
}

