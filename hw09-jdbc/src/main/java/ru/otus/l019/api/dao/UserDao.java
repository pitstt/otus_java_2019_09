package ru.otus.l019.api.dao;


import ru.otus.l019.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao<T> {

    Optional<Object> load(long id, Class<T> clazz);

    long create(T o);

    void update(T o);

    SessionManager getSessionManager();
}
