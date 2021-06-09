package ru.jm.springbootrestcrudsecapp.service;

import ru.jm.springbootrestcrudsecapp.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("expireUserService")
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRegistry sessionRegistry;

    @Override
    public void expireUserSessions(String username) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                UserDetails userDetails = (UserDetails) principal;
                if (userDetails.getUsername().equals(username)) {
                    for (SessionInformation information : sessionRegistry
                            .getAllSessions(userDetails, true)) {
                        information.expireNow();
                    }
                }
            }
        }
    }

    @Override
    public void expireUserSessions(Long id) {
        for (Object principal : sessionRegistry.getAllPrincipals()) {
            if (principal instanceof User) {
                User user = (User) principal;
                if (user.getId().equals(id)) {
                    for (SessionInformation information : sessionRegistry
                            .getAllSessions(user, true)) {
                        information.expireNow();
                    }
                }
            }
        }
    }
}
