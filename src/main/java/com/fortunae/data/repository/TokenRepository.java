package com.fortunae.data.repository;


import com.fortunae.data.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TokenRepository extends MongoRepository<Token, String> {
    Optional<Token> findByOwnerEmail(String lowerCase);
}
