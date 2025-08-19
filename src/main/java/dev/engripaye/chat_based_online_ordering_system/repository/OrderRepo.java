package dev.engripaye.chat_based_online_ordering_system.repository;

import dev.engripaye.chat_based_online_ordering_system.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
