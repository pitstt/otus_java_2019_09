package ru.otus.api.service;


import org.springframework.stereotype.Service;
import ru.otus.domain.User;


@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final HibernateTemplate hibernateTemplate;

    public UserAuthServiceImpl(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = (User) hibernateTemplate.findByLogin("admin").get();
        return user.getPassword().equals(password);
    }

}
