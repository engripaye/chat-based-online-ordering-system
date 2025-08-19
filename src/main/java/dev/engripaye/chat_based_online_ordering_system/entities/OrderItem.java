package dev.engripaye.chat_based_online_ordering_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // ✅ FK column
    private Order order;

    private Long menuItemId;
    private int qty;
    private BigDecimal price;
}
