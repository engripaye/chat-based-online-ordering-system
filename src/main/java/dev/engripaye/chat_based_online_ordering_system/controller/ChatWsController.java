package dev.engripaye.chat_based_online_ordering_system.controller;

import dev.engripaye.chat_based_online_ordering_system.dtos.BotReply;
import dev.engripaye.chat_based_online_ordering_system.dtos.ChatMessage;
import dev.engripaye.chat_based_online_ordering_system.service.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatWsController {

    private final ChatBotService botService;

    @MessageMapping("/chat")
    @SendToUser("/queue/replies")
    public BotReply chat(@AuthenticationPrincipal Jwt jwt, ChatMessage chatMessage){
        Long userId = jwt.getClaim("sub").hashCode() & 0xfffffffL;
        return botService.handle(userId, chatMessage.text());
    }

}
