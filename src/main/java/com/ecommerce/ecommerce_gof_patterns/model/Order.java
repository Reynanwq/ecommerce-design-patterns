package com.ecommerce.ecommerce_gof_patterns.model;


import com.ecommerce.ecommerce_gof_patterns.state.OrderProcessStateFactory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal shippingCost;

    @Column(nullable = false)
    private BigDecimal discount;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id", nullable = false)
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = OrderStatus.PENDING;
        }
        if (paymentStatus == null) {
            paymentStatus = PaymentStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

    /**
     * VERSÃO ANTIGA COM IF/ELSE (PROBLEMA):
     *
     * public void process() {
     *     if (status == OrderStatus.PENDING) {
     *         System.out.println("Processando pedido pendente...");
     *         status = OrderStatus.PROCESSING;
     *     } else if (status == OrderStatus.PROCESSING) {
     *         System.out.println("Pedido já está em processamento...");
     *     } else if (status == OrderStatus.SHIPPED) {
     *         System.out.println("Pedido já foi enviado, não pode ser processado novamente...");
     *     } else if (status == OrderStatus.DELIVERED) {
     *         System.out.println("Pedido já foi entregue...");
     *     } else if (status == OrderStatus.CANCELLED) {
     *         System.out.println("Pedido cancelado não pode ser processado...");
     *     } else if (status == OrderStatus.REFUNDED) {
     *         System.out.println("Pedido reembolsado não pode ser processado...");
     *     }
     * }
     */

    /**
     * VERSÃO NOVA COM STATE PATTERN (SOLUÇÃO):
     */
    public void process() {
        // Usa a factory para obter o estado correto baseado no status atual
        var state = OrderProcessStateFactory.getState(this.status);

        // Delega o processamento para o estado específico
        state.process(this);

        System.out.println("Status final do pedido " + id + ": " + this.status);
    }
}
