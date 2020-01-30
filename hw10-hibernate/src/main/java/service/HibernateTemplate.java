package service;

import java.util.Optional;

public interface HibernateTemplate<T> {

    long create(T o);

    Optional<T> load(long id, Class<T> clazz);

    void update(T o);

}
