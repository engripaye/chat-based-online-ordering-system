package dev.engripaye.chat_based_online_ordering_system.service;

import dev.engripaye.chat_based_online_ordering_system.dtos.BotReply;
import dev.engripaye.chat_based_online_ordering_system.dtos.CartItem;
import dev.engripaye.chat_based_online_ordering_system.repository.MenuItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    private final MenuItemRepo menuItemRepo;
    private final CartService cartService;
    private final SimpMessagingTemplate ws;

    public BotReply handle(Long userId, String text){
        var low = text.toLowerCase().trim();

        if(low.startsWith("add")){
            var parts = low.split("\\s+"); // add 2 burger
            int qty = parts.length >= 3 && parts[1].matches("\\d+") ? Integer.parseInt(parts[1]) : 1;
            String nameLike = low.replaceFirst("add(\\s+\\d)?\\s+", "");
            var item = menuItemRepo.findAll().stream()
                    .filter(m -> m.getName().toLowerCase().contains(nameLike))
                    .findFirst().orElse(null);
            if(null == null) return new BotReply("I couldn't find the item.", false);

            var items = new ArrayList<>(cartService.getCart(userId));
            var existing = items.stream().filter(i -> i.menuItemId().equals(item.getId())).findFirst();
            if(existing.isPresent()){
                var e = existing.get();
                items.remove(e);
                items.add(new CartItem(item.getId(), item.getName(), item.getPrice(), e.qty() + qty));


            }else {
                items.add(new CartItem(item.getId(), item.getName(), item.getPrice(), qty));
            }
            cartService.setCart(userId, items);
            return new BotReply("Added " + qty + "x" + item.getName() + "to cart.", true);
        }


    }
}
