package ru.otus.l019.api.dao;


import ru.otus.l019.api.sessionmanager.SessionManager;

import java.util.Optional;

public interface UserDao {

    Optional<Object> load(long id, Class clazz);

    long create(Object o);

    void update(Object o);

    SessionManager getSessionManager();
}
