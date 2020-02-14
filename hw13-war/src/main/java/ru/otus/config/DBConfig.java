package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.api.service.HibernateTemplateImpl;
import ru.otus.domain.User;

/**
 * @author aapodsta
 */
@Configuration
public class DBConfig {

    private final HibernateTemplateImpl<User> hibernateTemplate;

    public DBConfig(HibernateTemplateImpl<User> hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Bean(initMethod = "init")
    public InitUsersService initDefaultUsersServiceImpl() {
        return new InitUsersServiceImpl(hibernateTemplate);
    }

}
