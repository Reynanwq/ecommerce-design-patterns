package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class CancelledProcessState implements OrderProcessState{
    @Override
    public void process(Order order){
        System.out.println("Pedido cancelado não pode ser processado...");
    };

    @Override
    public OrderStatus getStatus(){
        return OrderStatus.CANCELLED;
    };
}
