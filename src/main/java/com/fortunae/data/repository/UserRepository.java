package com.fortunae.data.repository;

import com.fortunae.data.model.Role;
import com.fortunae.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Long countByRole(Role role);

    @Query("{'createdAt': {$gte: ?0}}")
    Long countNewSignups(LocalDate fromDate);

    @Query("{'isActive': true}")
    Long countActiveUsers();
}
