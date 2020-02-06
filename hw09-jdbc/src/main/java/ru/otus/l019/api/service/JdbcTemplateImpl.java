package ru.otus.l019.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyCache;
import ru.otus.l019.api.dao.UserDao;
import ru.otus.l019.api.model.TableObject;
import ru.otus.l019.api.sessionmanager.SessionManager;

import java.lang.ref.SoftReference;
import java.util.Optional;

public class JdbcTemplateImpl<T> implements JdbcTemplate<T> {

    private static Logger logger = LoggerFactory.getLogger(JdbcTemplateImpl.class);

    private static MyCache<String, Object> cache;

    private final UserDao userDao;

    public JdbcTemplateImpl(UserDao userDao,MyCache<String, Object> cache) {
        this.userDao = userDao;
        this.cache=cache;
        HwListener<Integer, Integer> listener =
                (key, value, action) -> logger.info("key:{}, value:{}, action: {}", key, value, action);
        cache.addListener(listener);
    }

    @Override
    public long create(Object user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.create(user);
                sessionManager.commitSession();
                cache.put(String.valueOf(userId), user);
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
                Object result = Optional.ofNullable(cache.get(String.valueOf(id)))
                        .orElse(null);
                if (result != null) {
                    logger.info("use Cache");
                    return (Optional<T>) Optional.of(result);
                }
                logger.info("use DB");
                Optional<Object> userOptional = userDao.load(id, clazz);
                cache.put(String.valueOf(id), userOptional);
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
