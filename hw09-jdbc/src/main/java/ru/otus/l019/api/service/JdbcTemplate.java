package ru.otus.l019.api.service;

import java.util.Optional;

public interface JdbcTemplate<T> {

    long create(T o);

    Optional<T> load(long id, Class<T> clazz);

    void update(T o);

}
