package ru.jm.springbootrestcrudsecapp.service;

import ru.jm.springbootrestcrudsecapp.entity.User;
import ru.jm.springbootrestcrudsecapp.dto.UserRequestDto;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {
    List<UserRequestDto> getAllUsers();

    UserRequestDto getUserById(Long id);

    void deleteUser(Long id);

    void saveUser(User user);

    void updateUserWithoutPassword(User user);

    void changeUserPassword(Long userId, String newPassword);

    static User dtoToEntity(UserRequestDto userDTO) {
        return User
                .builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .age(userDTO.getAge())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .roles(userDTO.getRoles().stream().map(RoleService::dtoToEntity).collect(Collectors.toSet()))
                .build();
    }

    static UserRequestDto entityToDto(User user) {
        return UserRequestDto
                .builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .age(user.getAge())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles().stream().map(RoleService::entityToDto).collect(Collectors.toSet()))
                .build();
    }
}