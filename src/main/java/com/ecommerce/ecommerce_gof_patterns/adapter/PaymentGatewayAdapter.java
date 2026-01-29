package com.ecommerce.ecommerce_gof_patterns.adapter;

public class PaymentGatewayAdapter implements PaymentProcessor {

    private final LegacyPaymentGateway legacyGateway;

    public PaymentGatewayAdapter(LegacyPaymentGateway legacyGateway) {
        this.legacyGateway = legacyGateway;
    }

    @Override
    public boolean processPayment(double amount, String currency) {
        // Adaptação SIMPLES
        String simulatedCardNumber = "4111111111111111"; // Card mock
        return legacyGateway.authorizePayment(simulatedCardNumber, amount);
    }
}