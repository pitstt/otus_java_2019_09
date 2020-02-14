package ru.otus.api.service;


import ru.otus.domain.User;

import java.util.List;
import java.util.Optional;

public interface HibernateTemplate<T> {

    Optional<User> findById(long id);

    Optional<User> findByLogin(String login);

    List<User> findAllUser();

    long create(User user);

}
