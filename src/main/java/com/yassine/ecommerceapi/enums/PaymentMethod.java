package com.yassine.ecommerceapi.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    CREDIT_CARD("Carte de crédit"),
    DEBIT_CARD("Carte de débit"),
    PAYPAL("PayPal"),
    BANK_TRANSFER("Virement bancaire"),
    APPLE_PAY("Apple Pay"),
    GOOGLE_PAY("Google Pay"),
    CRYPTO("Cryptomonnaie"),
    CASH_ON_DELIVERY("Paiement à la livraison");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

}