package com.earnwise.api.service;

import com.earnwise.api.domain.dto.CreateUserRequest;
//import com.earnwise.api.domain.model.Role;
import com.earnwise.api.domain.model.Role;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.repository.RoleRepository;
import com.earnwise.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User createUser(CreateUserRequest userRequest) {
        if(userRepository.findByUsername(userRequest.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }

        Set<Role> roles = new HashSet<>();
        var userRole = new Role();
        userRole.setName("ROLE_USER");
        roles.add(userRole);

        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getEmail());
        user.setAuthorities(roles);

        userRepository.save(user);
        return user;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserByEmail(String email) {
        return userRepository.findByUsername(email).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with username - %s, not found", username)));
    }
}
