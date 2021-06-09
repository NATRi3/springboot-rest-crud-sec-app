package ru.jm.springbootrestcrudsecapp.service;

import ru.jm.springbootrestcrudsecapp.entity.Role;
import ru.jm.springbootrestcrudsecapp.dto.RoleRequestDto;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    void saveRole(Role role);

    List<Role> getRoles();

    Role getRoleById(long id);

    static RoleRequestDto entityToDto(Role role) {
        RoleRequestDto roleDto = new RoleRequestDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());
        return roleDto;
    }
    static Role dtoToEntity(RoleRequestDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        return role;
    }

}
