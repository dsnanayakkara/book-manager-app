package com.dushanz.bookmanager.repository.security;


import com.dushanz.bookmanager.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findByRolesContaining(String roleName);
}

