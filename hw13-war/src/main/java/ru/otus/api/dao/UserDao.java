package ru.otus.api.dao;


import ru.otus.api.sessionmanager.SessionManager;
import ru.otus.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao<T> {

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAllUser();

    long create(User user);

    SessionManager getSessionManager();
}
