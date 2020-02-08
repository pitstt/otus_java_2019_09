package ru.otus.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.dao.UserDao;
import ru.otus.l019.api.sessionmanager.SessionManager;
import ru.otus.model.User;
import service.DbServiceException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HibernateTemplateImpl<T> implements HibernateTemplate<T> {
    private static Logger logger = LoggerFactory.getLogger(HibernateTemplateImpl.class);

    private final UserDao userDao;

    public HibernateTemplateImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> findById(long id) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);

                logger.info("object: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findByLogin(login);

                logger.info("object: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAllUser() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                List<User> list = userDao.findAllUser();

                logger.info("object: {}", list.toString());
                return list;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Collections.emptyList();
        }
    }

    @Override
    public long create(User user) {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userDao.create(user);
                sessionManager.commitSession();

                logger.info("created object: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }
}
