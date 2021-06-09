package ru.jm.springbootrestcrudsecapp.controllers;

import ru.jm.springbootrestcrudsecapp.entity.User;
import ru.jm.springbootrestcrudsecapp.dto.UserRequestDto;
import ru.jm.springbootrestcrudsecapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
public class UserRestController {

    @GetMapping("current")
    public ResponseEntity<UserRequestDto> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(UserService.entityToDto(currentUser));
    }
}
