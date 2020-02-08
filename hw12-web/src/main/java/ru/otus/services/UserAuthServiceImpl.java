package ru.otus.services;

import ru.otus.model.User;

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
