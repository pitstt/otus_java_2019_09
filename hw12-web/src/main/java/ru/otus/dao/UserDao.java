package ru.otus.dao;

import ru.otus.l019.api.sessionmanager.SessionManager;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAllUser();

    long create(User user);

    SessionManager getSessionManager();
}