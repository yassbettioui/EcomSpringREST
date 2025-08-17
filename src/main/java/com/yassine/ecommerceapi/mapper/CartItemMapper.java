package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.CartItemDTO;
import com.yassine.ecommerceapi.Entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "price", expression = "java(cartItem.getUnitPrice() * cartItem.getQuantity())")
    CartItemDTO toDTO(CartItem cartItem);

    CartItem toEntity(CartItemDTO cartItemDTO);
}
