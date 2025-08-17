package com.yassine.ecommerceapi.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("En attente"),
    COMPLETED("Complété"),
    FAILED("Échoué"),
    REFUNDED("Remboursé"),
    PARTIALLY_REFUNDED("Partiellement remboursé"),
    CANCELLED("Annulé"),
    EXPIRED("Expiré");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

}