package com.klef.jfsd.userservice.repository;

import com.klef.jfsd.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository  extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User getUserByEmail(String email);
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);

    void deleteByEmail(String email);

    boolean existsByEmail(String email);
}
