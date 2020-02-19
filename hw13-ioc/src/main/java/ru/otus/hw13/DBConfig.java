package ru.otus.hw13;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;
import ru.otus.hw13.hibernate.HibernateUtils;
import ru.otus.hw13.web.startup.UsersCreator;

@Configuration
public class DBConfig {
    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "/WEB-INF/config/hibernate.cfg.xml";

    private final ApplicationContext applicationContext;

    public DBConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE,
                User.class);
    }

    @Bean(initMethod = "createUsers")
    public UsersCreator userCreator(UserService userService) {
        return new UsersCreator(userService);
    }
}
