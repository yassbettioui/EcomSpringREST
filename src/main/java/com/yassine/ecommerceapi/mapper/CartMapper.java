package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.CartDTO;
import com.yassine.ecommerceapi.Dto.CartItemDTO;
import com.yassine.ecommerceapi.Entity.Cart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(source = "user.id", target = "userId")
    CartDTO cartToCartDTO(Cart cart);

    @Mapping(source = "userId", target = "user.id")
    Cart cartDTOToCart(CartDTO cartDTO);

    @AfterMapping
    default void calculateTotalPrice(Cart cart, @MappingTarget CartDTO cartDTO) {
        if (cartDTO.getItems() != null) {
            double total = cartDTO.getItems().stream()
                    .mapToDouble(CartItemDTO::getPrice)
                    .sum();
            cartDTO.setTotalPrice(total);
        }
    }
}

