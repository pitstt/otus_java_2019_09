package ru.otus.hw13.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.hw13.api.model.User;
import ru.otus.hw13.api.repository.UserRepository;
import ru.otus.hw13.api.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceCachedImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceCachedImpl.class);

    private final UserRepository userRepository;

    public UserServiceCachedImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public long create(User user) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                long userId = userRepository.create(user);
                sessionManager.commitSession();

                logger.info("saved user: {}", userId);
                return userId;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new UserServiceException(e);
            }
        }
    }


    @Override
    public Optional<User> findById(long id) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> mayBeUser = userRepository.findById(id);
                logger.info("loaded user: {}", mayBeUser.orElse(null));
                return mayBeUser;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userRepository.findByLogin(login);

                logger.info("loaded user by login: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                logger.error(e.getMessage(), e);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUser() {
        try (SessionManager sessionManager = userRepository.getSessionManager()) {
            sessionManager.beginSession();
            return userRepository.getAllUser();
        }
    }
}
