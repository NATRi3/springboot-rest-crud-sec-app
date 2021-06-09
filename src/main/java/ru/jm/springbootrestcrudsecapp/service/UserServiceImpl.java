package ru.jm.springbootrestcrudsecapp.service;

import ru.jm.springbootrestcrudsecapp.repository.UserRepository;
import ru.jm.springbootrestcrudsecapp.entity.User;
import ru.jm.springbootrestcrudsecapp.dto.UserRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public List<UserRequestDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserService::entityToDto).collect(Collectors.toList());
    }

    @Override
    public UserRequestDto getUserById(Long id) {
        return UserService.entityToDto(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id))
        );
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        sessionService.expireUserSessions(userRepository.save(user).getUsername());
    }

    @Override
    @Transactional
    public void updateUserWithoutPassword(User user){
        User oldUser = userRepository
                .findById(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(oldUser.getPassword());
        userRepository.save(user);
        sessionService.expireUserSessions(oldUser.getUsername());
    }


    @Override
    @Transactional
    public void changeUserPassword(Long userId, String newPassword) {
        userRepository.updatePasswordById(userId,encoder.encode(newPassword));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        sessionService.expireUserSessions(id);
    }
}