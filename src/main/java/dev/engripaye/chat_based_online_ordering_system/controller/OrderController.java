package dev.engripaye.chat_based_online_ordering_system.controller;

import dev.engripaye.chat_based_online_ordering_system.entities.Order;
import dev.engripaye.chat_based_online_ordering_system.repository.OrderRepo;
import dev.engripaye.chat_based_online_ordering_system.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderRepo orderRepo;
    private final CartService cartService;
    private final SimpMessagingTemplate ws;

    // suspended jwt for now
//    @PostMapping("/create")
//    public Order create(@AuthenticationPrincipal Jwt jwt){
//        Long userId = jwt.getClaim("sub").hashCode() & 0xfffffffL;
//
//        var items = cartService.getCart(userId);
//        var order = new Order();
//        order.setStatus("CREATED");
//        order.setTotal(items.stream()
//                .map(i -> i.price().multiply(BigDecimal.valueOf(i.qty())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add));
//
//        var saved = orderRepo.save(order);
//        // in real code: persist orderItem rows too
//        cartService.clear(userId);
//        ws.convertAndSend("/topic/order-updates", Map.of(
//                "orderId", saved.getId(), "status", saved.getStatus()));
//
//        return saved;
//
//    }

    @PostMapping("/create")
    public Order create(@RequestParam Long userId) {
        var items = cartService.getCart(userId);

        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Cart is empty, cannot create order.");
        }

        var order = new Order();
        order.setStatus("CREATED");
        order.setTotal(items.stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.qty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        var saved = orderRepo.save(order);
        cartService.clear(userId);

        ws.convertAndSend("/topic/order-updates", Map.of(
                "orderId", saved.getId(), "status", saved.getStatus()));

        return saved;
    }

    // Admin updates status
    @PatchMapping("/admin/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateStatus(@PathVariable Long id, @RequestParam String status){
        var o = orderRepo.findById(id).orElseThrow();
        o.setStatus(status);
        orderRepo.save(o);

        // Broadcast
        ws.convertAndSend("/topic/order-updates", Map.of("orderId", id, "status", status));
    }
}
