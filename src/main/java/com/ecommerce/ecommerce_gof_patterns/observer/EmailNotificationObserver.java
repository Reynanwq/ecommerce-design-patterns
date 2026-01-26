package com.ecommerce.ecommerce_gof_patterns.observer;

import com.ecommerce.ecommerce_gof_patterns.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailNotificationObserver implements OrderObserver {

    @Override
    public void onOrderStatusChanged(Order order) {
        log.info("📧 Sending email notification to customer {} for order {}",
                order.getCustomer().getEmail(),
                order.getOrderNumber());

        String message = buildEmailMessage(order);
        sendEmail(order.getCustomer().getEmail(), message);
    }

    private String buildEmailMessage(Order order) {
        return String.format(
                "Hello %s,\n\n" +
                        "Your order %s has been updated:\n" +
                        "Status: %s\n" +
                        "Payment Status: %s\n" +
                        "Total Amount: $%.2f\n\n" +
                        "Thank you for your purchase!",
                order.getCustomer().getName(),
                order.getOrderNumber(),
                order.getStatus(),
                order.getPaymentStatus(),
                order.getTotalAmount()
        );
    }

    private void sendEmail(String email, String message) {
        // Simulação de envio de email
        log.info("Email sent to {}: {}", email, message);
        // Aqui você integraria com um serviço real de email (JavaMail, SendGrid, etc.)
    }
}
