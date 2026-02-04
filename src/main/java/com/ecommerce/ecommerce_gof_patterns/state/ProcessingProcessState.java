package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class ProcessingProcessState extends AbstractOrderProcessState {

    @Override
    public void process(Order order) {
        System.out.println("Processando pedido em processamento...");
        System.out.println("Separando produtos...");
        System.out.println("Preparando embalagem...");

        // Estado decide mudar para SHIPPED
        if (podeEnviar(order)) {
            context.changeState(OrderStatus.SHIPPED);
            order.setStatus(OrderStatus.SHIPPED);
            System.out.println("Pedido " + order.getId() + " preparado para envio");
        }
    }

    private boolean podeEnviar(Order order) {
        return true;
    }

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PROCESSING;
    }
}