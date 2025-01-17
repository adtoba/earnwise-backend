package com.earnwise.api.service;

import com.earnwise.api.configuration.security.PasswordUtil;
import com.earnwise.api.domain.dto.CreateSocialRequest;
import com.earnwise.api.domain.dto.CreateUserRequest;
//import com.earnwise.api.domain.model.Role;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.dto.UserView;
import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.mapper.UserViewMapper;
import com.earnwise.api.domain.model.Role;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.repository.RoleRepository;
import com.earnwise.api.repository.SocialsRepository;
import com.earnwise.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService implements UserDetailsService {

    private final UserViewMapper userViewMapper;
    private final UserRepository userRepository;
    private final SocialsService socialsService;
    private final UserProfileService userProfileService;
    private final PasswordUtil passwordUtil;

    public UserService(UserRepository userRepository,
                       UserViewMapper userViewMapper,
                       PasswordUtil passwordUtil,
                       SocialsService socialsService,
                       UserProfileService userProfileService) {
        this.userRepository = userRepository;
        this.userViewMapper = userViewMapper;
        this.passwordUtil = passwordUtil;
        this.userProfileService = userProfileService;
        this.socialsService = socialsService;
    }

    @Transactional
    public UserView createUser(CreateUserRequest userRequest) {
        if(userRepository.findByUsername(userRequest.getEmail()).isPresent()) {
            throw new ValidationException("Email already exists");
        }

        Set<Role> roles = new HashSet<>();
        var userRole = new Role();
        userRole.setName("ROLE_USER");
        roles.add(userRole);

        User user = new User();
        user.setFullName(userRequest.getFullName());
        user.setPassword(passwordUtil.encode(userRequest.getPassword()));
        user.setUsername(userRequest.getEmail());
        user.setAuthorities(roles);

        User result = userRepository.save(user);

        // Create user profile for new account
        userProfileService.create(userRequest.getEmail());

        // Create a social profile for new account
        CreateSocialRequest request = new CreateSocialRequest();
        request.setUserId(result.getId());
        request.setInstagram(null);
        request.setTwitter(null);
        request.setLinkedIn(null);
        socialsService.create(request);

        return userViewMapper.toUserView(user);
    }


    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByUsername(email);
        if(user.isEmpty()) {
            throw new BadCredentialsException("User not found");
        } else {
            return user.get();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with username - %s, not found", username)));
    }
}
