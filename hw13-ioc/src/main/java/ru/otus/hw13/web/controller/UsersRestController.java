package ru.otus.hw13.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.api.service.UserServiceException;

import java.util.List;

@RestController
public class UsersRestController {

    private final UserService userService;

    public UsersRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public List<User> allUsers() {
        return userService.getAllUser();
    }

    @PostMapping("/api/user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User newUser = new User(user.getName(),user.getLogin(),user.getPassword());
        try {
            userService.create(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (UserServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        }
    }
}
