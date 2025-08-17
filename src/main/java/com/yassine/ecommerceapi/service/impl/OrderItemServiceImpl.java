package com.yassine.ecommerceapi.service.impl;

import com.yassine.ecommerceapi.Dto.OrderItemDTO;
import com.yassine.ecommerceapi.Entity.OrderItem;
import com.yassine.ecommerceapi.mapper.OrderItemMapper;
import com.yassine.ecommerceapi.repository.OrderItemRepository;
import com.yassine.ecommerceapi.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItemDTO addOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemMapper.orderItemDTOToOrderItem(orderItemDTO);
        return orderItemMapper.orderItemToOrderItemDTO(orderItemRepository.save(orderItem));
    }

    @Override
    public OrderItemDTO getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::orderItemToOrderItemDTO)
                .orElseThrow(() -> new RuntimeException("OrderItem not found"));
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemRepository.findAll()
                .stream()
                .map(orderItemMapper::orderItemToOrderItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}

