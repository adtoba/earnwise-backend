package com.earnwise.api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
public class Socials {
    @Id
    @UuidGenerator
    private String id;

    private String instagram;
    private String twitter;
    private String linkedIn;

    private String userId;
}
