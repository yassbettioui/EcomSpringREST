package com.yassine.ecommerceapi.mapper;

import com.yassine.ecommerceapi.Dto.PaymentDTO;
import com.yassine.ecommerceapi.Entity.Payment;
import com.yassine.ecommerceapi.enums.PaymentMethod;
import com.yassine.ecommerceapi.enums.PaymentStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "paymentMethod", source = "paymentMethod", qualifiedByName = "paymentMethodToString")
    @Mapping(target = "status", source = "status", qualifiedByName = "paymentStatusToString")
    PaymentDTO toDto(Payment payment);

    @Mapping(target = "order", ignore = true) // Géré séparément dans le service
    @Mapping(target = "paymentMethod", source = "paymentMethod", qualifiedByName = "stringToPaymentMethod")
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToPaymentStatus")
    Payment toEntity(PaymentDTO paymentDTO);

    @Named("paymentMethodToString")
    default String paymentMethodToString(PaymentMethod method) {
        return method != null ? method.name() : null;
    }

    @Named("stringToPaymentMethod")
    default PaymentMethod stringToPaymentMethod(String method) {
        try {
            return method != null ? PaymentMethod.valueOf(method) : null;
        } catch (IllegalArgumentException e) {
            return null; // ou une valeur par défaut
        }
    }

    @Named("paymentStatusToString")
    default String paymentStatusToString(PaymentStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToPaymentStatus")
    default PaymentStatus stringToPaymentStatus(String status) {
        try {
            return status != null ? PaymentStatus.valueOf(status) : null;
        } catch (IllegalArgumentException e) {
            return null; // ou une valeur par défaut
        }
    }

    @AfterMapping
    default void afterToEntity(@MappingTarget Payment payment, PaymentDTO paymentDTO) {
        // Vous pouvez ajouter ici une logique post-mapping si nécessaire
    }

    @AfterMapping
    default void afterToDto(@MappingTarget PaymentDTO paymentDTO, Payment payment) {
        // Vous pouvez ajouter ici une logique post-mapping si nécessaire
    }
}