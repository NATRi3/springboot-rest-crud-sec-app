package ru.jm.springbootrestcrudsecapp.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import ru.jm.springbootrestcrudsecapp.entity.Role;
import ru.jm.springbootrestcrudsecapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    void updatePasswordById(@Param("id") Long id,
                            @Param("password") String password);
}

