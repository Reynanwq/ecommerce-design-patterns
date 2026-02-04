package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class RefundedProcessState extends AbstractOrderProcessState {

    @Override
    public void process(Order order) {
        System.out.println("Pedido reembolsado...");
        System.out.println("Processo finalizado...");
        // Estado final - sem transições
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.REFUNDED;
    }
}