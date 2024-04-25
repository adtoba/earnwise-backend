package com.earnwise.api.controller;

import com.earnwise.api.configuration.security.JwtTokenUtil;
import com.earnwise.api.domain.dto.AuthRequest;
import com.earnwise.api.domain.dto.CreateUserRequest;
import com.earnwise.api.domain.model.User;
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

import java.util.List;

@RestController
@RequestMapping(path = "api/v1")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("auth/register")
    public User createUser(@RequestBody @Valid CreateUserRequest request) {
       return userService.createUser(request);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            final User user = userService.getUserByEmail(authenticate.getPrincipal().toString());

            return ResponseEntity.ok().body(jwtTokenUtil.generateToken(user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("auth/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
