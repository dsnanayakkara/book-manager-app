package com.dushanz.bookmanager.service.security;

import com.dushanz.bookmanager.entity.security.User;
import com.dushanz.bookmanager.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getAdminUser() {
        // Fetch admin user by role
        return userRepository.findByRolesContaining("ROLE_ADMIN")
                .orElseThrow(() -> new UsernameNotFoundException("Admin user not found"));
    }

}
