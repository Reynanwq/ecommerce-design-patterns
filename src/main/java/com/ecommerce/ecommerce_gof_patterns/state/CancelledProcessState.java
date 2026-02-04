package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class CancelledProcessState extends AbstractOrderProcessState {

    @Override
    public void process(Order order) {
        System.out.println("Pedido cancelado...");
        System.out.println("Processando cancelamento...");

        // Cancelado pode virar REFUNDED
        if (precisaReembolso(order)) {
            context.changeState(OrderStatus.REFUNDED);
            order.setStatus(OrderStatus.REFUNDED);
            System.out.println("Iniciando processo de reembolso...");
        }
    }

    private boolean precisaReembolso(Order order) {
        return true;
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.CANCELLED;
    }
}