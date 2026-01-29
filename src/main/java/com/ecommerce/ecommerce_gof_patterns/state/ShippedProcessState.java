package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class ShippedProcessState implements OrderProcessState{
    @Override
    public void process(Order order){
        System.out.println("Pedido já foi enviado, não pode ser processado novamente...");
    };

    @Override
    public OrderStatus getStatus(){
        return OrderStatus.SHIPPED;
    };
}
