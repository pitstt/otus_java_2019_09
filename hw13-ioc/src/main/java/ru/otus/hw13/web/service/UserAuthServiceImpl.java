package ru.otus.hw13.web.service;


import org.springframework.stereotype.Service;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.service.UserService;


@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserService hibernateTemplate;

    public UserAuthServiceImpl(UserService hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = (User) hibernateTemplate.findByLogin("admin").get();
        return user.getPassword().equals(password);
    }

}
