package com.earnwise.api.repository;

import com.earnwise.api.domain.model.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {
    List<Call> findAllByUserId(String userId);
    
    List<Call> findAllByStatusAndUserId(String status, String userId);

    List<Call> findAllByStatusAndExpertId(String status, String expertId);
    

    @Query("SELECT c FROM calls c WHERE c.userId = :userId OR c.expertId = :expertId")
    List<Call> findByUserIdOrExpertIdCustom(@Param("userId") String userId, @Param("expertId") String expertId);
}
