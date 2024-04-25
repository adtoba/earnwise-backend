package com.earnwise.api.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity(name = "calls")
@Data
public class Call {
    @Id
    @UuidGenerator
    private String id;

    private String createdBy;

//    public Call() {
//
//    }
//
//    public Call(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }

}
