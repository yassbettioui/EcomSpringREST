package com.yassine.ecommerceapi.mapper;
import com.yassine.ecommerceapi.Dto.DeliveryDTO;
import com.yassine.ecommerceapi.Entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(source = "order.id", target = "orderId")
    DeliveryDTO deliveryToDeliveryDTO(Delivery delivery);

    @Mapping(source = "orderId", target = "order.id")
    Delivery deliveryDTOToDelivery(DeliveryDTO deliveryDTO);
}
