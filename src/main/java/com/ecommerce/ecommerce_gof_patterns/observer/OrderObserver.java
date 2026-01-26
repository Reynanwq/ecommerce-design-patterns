package com.ecommerce.ecommerce_gof_patterns.observer;

import com.ecommerce.ecommerce_gof_patterns.model.Order;

public interface OrderObserver {
    void onOrderStatusChanged(Order order);
}
