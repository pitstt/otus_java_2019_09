package ru.otus.l019.api.service;

import java.util.Optional;

public interface DBServiceUser {

    long crate(Object o);

    Optional<Object> load(long id, Class clazz);

    void update(Object o);

}
