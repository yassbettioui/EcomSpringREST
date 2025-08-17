package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.OrderItemDTO;
import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.Entity.OrderItem;
import com.yassine.ecommerceapi.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName") // Ajoutez cette ligne
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);

    @Mapping(target = "order", expression = "java(orderFromId(orderItemDTO.getOrderId()))")
    @Mapping(target = "product", expression = "java(productFromId(orderItemDTO.getProductId()))")
    OrderItem orderItemDTOToOrderItem(OrderItemDTO orderItemDTO);

    default Order orderFromId(Long id) {
        if (id == null) return null;
        Order order = new Order();
        order.setId(id);
        return order;
    }

    default Product productFromId(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }
}