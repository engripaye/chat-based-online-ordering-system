package dev.engripaye.chat_based_online_ordering_system.controller;

import dev.engripaye.chat_based_online_ordering_system.dtos.CartItem;
import dev.engripaye.chat_based_online_ordering_system.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    @GetMapping public List<CartItem> get(@AuthenticationPrincipal Jwt jwt){
        return cartService.getCart(jwt.getClaim("sub").hashCode() & 0xfffffffL); // demo userId
    }
}
