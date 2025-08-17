package com.yassine.ecommerceapi.service;


import com.yassine.ecommerceapi.Dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemDTO addOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO getOrderItemById(Long id);
    List<OrderItemDTO> getAllOrderItems();
    void deleteOrderItem(Long id);
}
