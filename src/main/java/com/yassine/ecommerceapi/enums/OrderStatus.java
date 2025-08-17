package com.yassine.ecommerceapi.enums;

public enum OrderStatus {
    PENDING("En attente"),
    PROCESSING("En traitement"),
    SHIPPED("Expédiée"),
    DELIVERED("Livrée"),
    CANCELLED("Annulée"),
    REFUNDED("Remboursée");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Méthode pour vérifier les transitions valides
    public boolean canTransitionTo(OrderStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == PROCESSING || newStatus == CANCELLED;
            case PROCESSING -> newStatus == SHIPPED || newStatus == CANCELLED;
            case SHIPPED -> newStatus == DELIVERED;
            case DELIVERED -> newStatus == REFUNDED;
            case CANCELLED, REFUNDED -> false;
        };
    }
}