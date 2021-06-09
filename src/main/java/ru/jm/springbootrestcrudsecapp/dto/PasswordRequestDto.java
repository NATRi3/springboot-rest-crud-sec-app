package ru.jm.springbootrestcrudsecapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequestDto {

    @NotNull
    private Long id;

    @NotEmpty(message = "Enter password")
    private String password;

}
