package ru.otus.config;

import ru.otus.api.service.HibernateTemplateImpl;
import ru.otus.domain.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aapodsta
 */
public class InitUsersServiceImpl implements InitUsersService{

    private final HibernateTemplateImpl hibernateTemplate;

    private static Map<Long, User> users = new HashMap<>();

    InitUsersServiceImpl(HibernateTemplateImpl hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public void init() {
        users.put(1L, new User("Крис Гир", "user1", "11111"));
        users.put(2L, new User("Ая Кэш", "user2", "11111"));
        users.put(3L, new User("Десмин Боргес", "user3", "11111"));
        users.put(4L, new User("Кетер Донохью", "user4", "11111"));
        users.put(5L, new User("Стивен Шнайдер", "user5", "11111"));
        users.put(6L, new User("Джанет Вэрни", "user6", "11111"));
        users.put(7L, new User("Брэндон Смит", "user7", "11111"));
        users.put(8L, new User("Artem", "admin", "admin"));
        for (Map.Entry<Long, User> entry : users.entrySet()) {
            hibernateTemplate.create(entry.getValue());
        }
    }

}
