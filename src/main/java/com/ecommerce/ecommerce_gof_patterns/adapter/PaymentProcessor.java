package com.ecommerce.ecommerce_gof_patterns.adapter;

public interface PaymentProcessor {
    boolean processPayment(double amount, String currency);
}
