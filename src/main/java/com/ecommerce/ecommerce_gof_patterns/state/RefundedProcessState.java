package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class RefundedProcessState implements OrderProcessState{
    @Override
    public void process(Order order){
        System.out.println("Pedido reembolsado não pode ser processado...");
    };

    @Override
    public OrderStatus getStatus(){
        return OrderStatus.REFUNDED;
    };
}
