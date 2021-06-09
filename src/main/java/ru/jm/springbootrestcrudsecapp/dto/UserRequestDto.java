package ru.jm.springbootrestcrudsecapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private Long id;

    @Email(message = "Wrong email")
    @NotEmpty(message = "Enter email")
    private String username;

    private String password;

    @Min(value = 0,message = "Age must be more than 0")
    @Max(value = 100,message = "Age must be less than 100")
    @NotNull(message = "Chose your age")
    private Integer age;

    @Size(min = 5,max = 10, message = "First name must be more than 5 chars and less than 10")
    private String firstName;

    @Size(min = 5,max = 10, message = "Last name must be more than 5 chars and less than 10")
    private String lastName;

    @NotEmpty(message = "Chose role!")
    private Set<RoleRequestDto> roles;
}
