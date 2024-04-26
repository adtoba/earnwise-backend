package com.earnwise.api.controller;

import com.earnwise.api.configuration.security.JwtTokenUtil;
import com.earnwise.api.domain.dto.AuthRequest;
import com.earnwise.api.domain.dto.CreateUserRequest;
import com.earnwise.api.domain.dto.UserProfileView;
import com.earnwise.api.domain.dto.UserView;
import com.earnwise.api.domain.mapper.UserViewMapper;
import com.earnwise.api.domain.model.User;
import com.earnwise.api.domain.model.UserProfile;
import com.earnwise.api.service.UserProfileService;
import com.earnwise.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserViewMapper userViewMapper;

    @Autowired
    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JwtTokenUtil jwtTokenUtil,
                          UserViewMapper userViewMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userViewMapper = userViewMapper;
    }

    @PostMapping("auth/register")
    public UserView createUser(@RequestBody @Valid CreateUserRequest request) {
       return userService.createUser(request);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            final User user = userService.getUserByEmail(authenticate.getPrincipal().toString());
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtTokenUtil.generateToken(user));
            response.put("user", userViewMapper.toUserView(user));

            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
