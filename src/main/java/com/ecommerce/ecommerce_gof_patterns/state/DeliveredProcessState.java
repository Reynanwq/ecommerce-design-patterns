package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class DeliveredProcessState extends AbstractOrderProcessState {

    @Override
    public void process(Order order) {
        System.out.println("Pedido já foi entregue...");
        System.out.println("Solicitando avaliação do cliente...");

        // Estado final - pode ir para REFUNDED se houver problema
        if (precisaReembolso(order)) {
            context.changeState(OrderStatus.REFUNDED);
            order.setStatus(OrderStatus.REFUNDED);
        }
    }

    private boolean precisaReembolso(Order order) {
        return false;
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.DELIVERED;
    }
}