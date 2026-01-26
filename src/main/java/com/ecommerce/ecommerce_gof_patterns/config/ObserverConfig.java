package com.ecommerce.ecommerce_gof_patterns.config;

import com.ecommerce.ecommerce_gof_patterns.observer.EmailNotificationObserver;
import com.ecommerce.ecommerce_gof_patterns.observer.OrderSubject;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ObserverConfig {

    private final OrderSubject orderSubject;
    private final EmailNotificationObserver emailObserver;

    @PostConstruct
    public void registerObservers() {
        orderSubject.addObserver(emailObserver);
    }
}
