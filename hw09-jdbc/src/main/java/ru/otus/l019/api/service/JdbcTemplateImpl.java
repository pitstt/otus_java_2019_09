package ru.otus.l019.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.sessionmanager.SessionManager;

import java.lang.ref.SoftReference;
import java.util.Optional;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private static Logger logger = LoggerFactory.getLogger(JdbcTemplateImpl.class);

    private static MyCache<Long, SoftReference<Object>> cache = new MyCache<>();

    private final UserDao userDao;

    public JdbcTemplateImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public long create(Object user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.create(user);
                sessionManager.commitSession();
                cache.put(userId, new SoftReference<>(user));
                logger.info("created object: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<T> load(long id, Class<T> clazz) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                if(cache.get(id)!=null) {
                    Optional<Object> result = Optional.ofNullable(cache.get(id).get());
                    logger.info("use Cache");
                    return (Optional<T>) result;
                }
                logger.info("use DB");
                Optional<Object> userOptional = userDao.load(id, clazz);

                logger.info("object: {}", userOptional.orElse(null));
                return (Optional<T>) userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public void update(T o) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.update(o);
                sessionManager.commitSession();

                logger.info("update object");
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

}
