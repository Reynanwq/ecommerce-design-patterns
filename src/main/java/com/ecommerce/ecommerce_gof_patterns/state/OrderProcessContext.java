package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class OrderProcessContext {
    private OrderProcessState currentState;

    public OrderProcessContext(OrderStatus initialState) {
        this.currentState = OrderProcessStateFactory.getState(initialState);
        this.currentState.setContext(this); // Passa referência do contexto
    }

    public void process(com.ecommerce.ecommerce_gof_patterns.model.Order order) {
        currentState.process(order);
    }

    public OrderStatus getStatus() {
        return currentState.getStatus();
    }

    protected void changeState(OrderStatus newStatus) {
        this.currentState = OrderProcessStateFactory.getState(newStatus);
        this.currentState.setContext(this);
        System.out.println("Estado alterado para: " + newStatus);
    }
}