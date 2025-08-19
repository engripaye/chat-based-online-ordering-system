package dev.engripaye.chat_based_online_ordering_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.engripaye.chat_based_online_ordering_system.dtos.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper om = new ObjectMapper();

    // ✅ Correct key generator
    private String key(Long userId) {
        return "cart:" + userId;
    }

    public List<CartItem> getCart(Long userId) {
        var json = redisTemplate.opsForValue().get(key(userId));
        try {
            return json == null ? List.of() :
                    Arrays.asList(om.readValue(json, CartItem[].class));
        } catch (Exception e) {
            return List.of();
        }
    }

    public void setCart(Long userId, List<CartItem> items) {
        try {
            redisTemplate.opsForValue().set(key(userId), om.writeValueAsString(items));
        } catch (Exception ignored) {}
    }

    public void clear(Long userId) {
        redisTemplate.delete(key(userId));
    }
}
