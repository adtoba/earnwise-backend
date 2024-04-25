package com.earnwise.api.repository;

import com.earnwise.api.domain.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {
    List<Call> findAllByCreatedBy(String createdBy);
}
