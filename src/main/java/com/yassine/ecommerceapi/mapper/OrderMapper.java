package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.OrderDTO;
import com.yassine.ecommerceapi.Entity.Order;
import com.yassine.ecommerceapi.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, PaymentMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "payment", target = "payment")
    OrderDTO orderToOrderDTO(Order order);

    @Mapping(target = "customer", expression = "java(userFromId(orderDTO.getCustomerId()))")
    @Mapping(target = "payment", source = "payment")
    Order orderDTOToOrder(OrderDTO orderDTO);

    default User userFromId(Long id) {
        if (id == null) return null;
        User user = new User();
        user.setId(id);
        return user;
    }
}
