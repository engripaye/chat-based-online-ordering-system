package dev.engripaye.chat_based_online_ordering_system.repository;

import dev.engripaye.chat_based_online_ordering_system.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
}
