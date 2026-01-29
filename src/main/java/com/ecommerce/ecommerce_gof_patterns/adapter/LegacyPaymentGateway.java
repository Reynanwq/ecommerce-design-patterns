package com.ecommerce.ecommerce_gof_patterns.adapter;

public class LegacyPaymentGateway {
    public boolean authorizePayment(String cardNumber, double value) {
        System.out.println("Processando pagamento legado: " + value);
        return true; // Simulação
    }
}
