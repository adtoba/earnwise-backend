package com.earnwise.api.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Indexed;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Table(name = "users")
@Data
public class User implements UserDetails, Serializable {
    @Id
    @GenericGenerator(name = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    private UUID id;

    private String username;
    private String password;

    private String fullName;

    private boolean enabled;
    private Set<Role> authorities = new HashSet<Role>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;


    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
    }
    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
