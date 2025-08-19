package dev.engripaye.chat_based_online_ordering_system.controller;

import dev.engripaye.chat_based_online_ordering_system.entities.MenuItem;
import dev.engripaye.chat_based_online_ordering_system.repository.MenuItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemRepo menuItemRepo;

    @GetMapping("/menu")
    public List<MenuItem>list(){
        return menuItemRepo.findAll();
    }
}
