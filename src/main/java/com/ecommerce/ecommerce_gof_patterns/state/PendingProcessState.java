package com.ecommerce.ecommerce_gof_patterns.state;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;

public class PendingProcessState implements OrderProcessState{

    @Override
    public void process(Order order){
        System.out.println("Processando pedido pendente...");
        System.out.println("Validando estoque...");
        System.out.println("Gerando fatura...");
        System.out.println("Notificando usuário...");

        order.setStatus(OrderStatus.PROCESSING);
        System.out.println("Pedido " + order.getId() + " mudou para PROCESSING");
    };

    @Override
    public OrderStatus getStatus(){
        return OrderStatus.PENDING;
    };
}
