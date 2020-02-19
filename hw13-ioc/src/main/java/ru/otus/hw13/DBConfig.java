package ru.otus.hw13;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.hibernate.HibernateUtils;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DBConfig {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "/WEB-INF/config/hibernate.cfg.xml";

    private final ApplicationContext applicationContext;

    private static Map<Long, User> users = new HashMap<>();

    public DBConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class);
    }

    @Bean
    public void userCreator() {
        UserService userService = applicationContext.getBean(UserService.class);
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
