package dev.engripaye.chat_based_online_ordering_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private String status; // CREATED, PAID, PREPARING, READY, DELIVERED
    private BigDecimal total;
    private Instant createdAt = Instant.now();
    @OneToMany(mappedBy = "orders", cascade=ALL) private List<OrderItem> Items = new ArrayList<>();
}
