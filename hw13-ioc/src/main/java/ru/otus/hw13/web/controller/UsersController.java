package ru.otus.hw13.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    @GetMapping("/users")
    public String viewUsersPage() {
        return "users.html";
    }

}
