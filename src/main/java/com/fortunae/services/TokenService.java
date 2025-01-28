package com.fortunae.services;


import com.fortunae.data.model.Token;

public interface TokenService {

    String  createToken(String email);

    Token findByUserEmail(String email);

    void deleteToken(String id);

}
