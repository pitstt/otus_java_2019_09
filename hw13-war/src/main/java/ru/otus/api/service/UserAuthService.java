package ru.otus.api.service;

public interface UserAuthService {
    boolean authenticate(String login, String password);
}
