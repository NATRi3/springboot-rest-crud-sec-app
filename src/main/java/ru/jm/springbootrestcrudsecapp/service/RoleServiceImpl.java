package ru.jm.springbootrestcrudsecapp.service;

import ru.jm.springbootrestcrudsecapp.repository.RoleRepository;
import ru.jm.springbootrestcrudsecapp.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> roleFound = roleRepository.findById(id);
        return roleFound.orElseThrow(() -> new RuntimeException("Role not found with id " + id));
    }
}
