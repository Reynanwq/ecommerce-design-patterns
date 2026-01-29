package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrderProcessStateFactory {
    private static final Map<OrderStatus, OrderProcessState> states = new ConcurrentHashMap<>();

    //bloco que executa automaticamente quando a classe é carregada na memória.
    static {
        states.put(OrderStatus.PENDING, new PendingProcessState());
        states.put(OrderStatus.PROCESSING, new ProcessingProcessState());
        states.put(OrderStatus.SHIPPED, new ShippedProcessState());
        states.put(OrderStatus.DELIVERED, new DeliveredProcessState());
        states.put(OrderStatus.CANCELLED, new CancelledProcessState());
        states.put(OrderStatus.REFUNDED, new RefundedProcessState());
    }

    public static OrderProcessState getState(OrderStatus status) {
        OrderProcessState state = states.get(status);
        if (state == null) {
            throw new IllegalArgumentException("Estado não suportado: " + status);
        }
        return state;
    }

}
