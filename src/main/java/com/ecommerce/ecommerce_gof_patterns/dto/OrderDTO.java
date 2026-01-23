package com.ecommerce.ecommerce_gof_patterns.dto;

import com.ecommerce.ecommerce_gof_patterns.model.OrderStatus;
import com.ecommerce.ecommerce_gof_patterns.model.PaymentMethod;
import com.ecommerce.ecommerce_gof_patterns.model.PaymentStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;

    private String orderNumber;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotEmpty(message = "Order must have at least one item")
    private List<OrderItemDTO> items;

    private OrderStatus status;

    private BigDecimal subtotal;

    private BigDecimal shippingCost;

    private BigDecimal discount;

    private BigDecimal totalAmount;

    @NotNull(message = "Shipping address ID is required")
    private Long shippingAddressId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String notes;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
