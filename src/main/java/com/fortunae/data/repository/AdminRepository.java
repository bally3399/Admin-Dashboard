package com.fortunae.data.repository;

import com.fortunae.data.model.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {

    Admin findByEmail(String email);
}
