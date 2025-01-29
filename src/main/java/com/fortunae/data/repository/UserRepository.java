package com.fortunae.data.repository;

import com.fortunae.data.model.Role;
import com.fortunae.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    long countByRole(Role role);
}
