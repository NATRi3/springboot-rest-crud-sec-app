package ru.jm.springbootrestcrudsecapp.service;


public interface SessionService {

    void expireUserSessions(String username);

    void expireUserSessions(Long id);
}
