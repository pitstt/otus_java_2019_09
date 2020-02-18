package ru.otus.hw13.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String view() {
        return "users.html";
    }


    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        AtomicReference<RedirectView> redirectView = new AtomicReference<>();
        if (userService.findByLogin(user.getLogin()).equals(Optional.empty())) {
            redirectView.set(new RedirectView("users.htm", true));
        }
        return redirectView.get();
    }

}
