package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class ProcessingProcessState implements OrderProcessState{
    @Override
    public void process(Order order){
        System.out.println("Pedido já está em processamento...");
    };

    @Override
    public OrderStatus getStatus(){
        return OrderStatus.PROCESSING;
    };
}

