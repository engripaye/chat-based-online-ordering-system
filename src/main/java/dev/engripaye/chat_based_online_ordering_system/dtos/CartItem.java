package dev.engripaye.chat_based_online_ordering_system.dtos;

import java.math.BigDecimal;

public record CartItem(
        Long menuItemId,
        String name,
        BigDecimal price,
        int qty)
{ }
