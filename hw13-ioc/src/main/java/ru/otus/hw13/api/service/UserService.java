package ru.otus.hw13.api.service;


import ru.otus.hw13.api.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  long create(User user);

  Optional<User> findById(long id);

  Optional<User> findByLogin(String login);

  List<User> getAllUser();

}
