package ru.otus.hw13.web.startup;

import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class UsersCreator {

    private final UserService userService;

    private static Map<Long, User> users = new HashMap<>();

    public UsersCreator(UserService userService) {
        this.userService = userService;
    }

    public void createUsers() {
        users.put(1L, new User("Крис Гир", "user1", "11111"));
        users.put(2L, new User("Ая Кэш", "user2", "11111"));
        users.put(3L, new User("Десмин Боргес", "user3", "11111"));
        users.put(4L, new User("Кетер Донохью", "user4", "11111"));
        users.put(5L, new User("Стивен Шнайдер", "user5", "11111"));
        users.put(6L, new User("Джанет Вэрни", "user6", "11111"));
        users.put(7L, new User("Брэндон Смит", "user7", "11111"));
        users.put(8L, new User("Artem", "admin", "admin"));
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            userService.create(entry.getValue());
        }
    }
}
