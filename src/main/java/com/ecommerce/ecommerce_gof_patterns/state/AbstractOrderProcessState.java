package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;

public abstract class AbstractOrderProcessState implements OrderProcessState {
    protected OrderProcessContext context;

    @Override
    public void setContext(OrderProcessContext context) {
        this.context = context;
    }
}