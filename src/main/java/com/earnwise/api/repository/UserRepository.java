package com.earnwise.api.repository;

import com.earnwise.api.domain.exception.NotFoundException;
import com.earnwise.api.domain.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    default User getUserById(String id) {
        Optional<User> optionalUser = findById(id);
        if(optionalUser.isEmpty()) {
            throw new NotFoundException(User.class, id);
        }

        if(!optionalUser.get().isEnabled()) {
            throw new NotFoundException(User.class, id);
        }
        return optionalUser.get();
    }

}
