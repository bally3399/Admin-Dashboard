package com.fortunae.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fortunae.execptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import static com.fortunae.execptions.ExceptionMessages.INVALID_AUTHORIZATION_HEADER_EXCEPTION;
import static com.fortunae.execptions.ExceptionMessages.VERIFICATION_FAILED_EXCEPTION;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtUtils {


    public static String generateAccessToken(String id){
        return JWT.create()
                .withClaim("user_id", id)
                .withIssuer("quagga_db")
                .sign(Algorithm.HMAC256("secret"));
    }

}
