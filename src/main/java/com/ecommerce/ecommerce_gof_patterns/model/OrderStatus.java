package com.ecommerce.ecommerce_gof_patterns.model;

public enum OrderStatus {
    PENDING,           // Aguardando pagamento
    CONFIRMED,         // Pagamento confirmado
    PROCESSING,        // Em processamento
    SHIPPED,          // Enviado
    DELIVERED,        // Entregue
    CANCELLED,        // Cancelado
    REFUNDED          // Reembolsado
}

