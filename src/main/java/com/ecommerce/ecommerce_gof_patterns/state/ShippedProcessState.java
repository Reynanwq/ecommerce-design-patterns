package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class ShippedProcessState extends AbstractOrderProcessState {

    @Override
    public void process(Order order) {
        System.out.println("Pedido já foi enviado...");
        System.out.println("Rastreando entrega...");

        // Estado muda para DELIVERED após "entrega"
        if (foiEntregue(order)) {
            context.changeState(OrderStatus.DELIVERED);
            order.setStatus(OrderStatus.DELIVERED);
            System.out.println("Pedido " + order.getId() + " entregue!");
        }
    }

    private boolean foiEntregue(Order order) {
        return true;
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.SHIPPED;
    }
}