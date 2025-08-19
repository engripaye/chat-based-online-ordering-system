package dev.engripaye.chat_based_online_ordering_system.dtos;

import java.util.List;

public record CreateOrderRequest(
        List<CartItem> items) {
}
