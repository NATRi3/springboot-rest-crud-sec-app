package ru.jm.springbootrestcrudsecapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jm.springbootrestcrudsecapp.entity.User;
import ru.jm.springbootrestcrudsecapp.dto.PasswordRequestDto;
import ru.jm.springbootrestcrudsecapp.dto.RoleRequestDto;
import ru.jm.springbootrestcrudsecapp.dto.UserRequestDto;
import ru.jm.springbootrestcrudsecapp.service.RoleService;
import ru.jm.springbootrestcrudsecapp.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/")
@AllArgsConstructor
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserRequestDto>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserRequestDto> showUserInfo(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserRequestDto userDTO) {
        userService.saveUser(UserService.dtoToEntity(userDTO));
        return ResponseEntity.ok().build();
    }


    @PutMapping("/pass")
    public ResponseEntity<Void> updateUserPassword(@Valid @RequestBody PasswordRequestDto passwordRequest){
        userService.changeUserPassword(passwordRequest.getId(),passwordRequest.getPassword());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequestDto userDTO) {
        userService.updateUserWithoutPassword(UserService.dtoToEntity(userDTO));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping("roles")
    public ResponseEntity<List<RoleRequestDto>> getPossibleRoles() {
        return ResponseEntity.ok(roleService.getRoles().stream().map(RoleService::entityToDto).collect(Collectors.toList()));
    }
}
