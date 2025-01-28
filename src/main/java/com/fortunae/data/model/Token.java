package com.fortunae.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Document("Tokens")
public class Token {

    @Id
    private String id;

    private String token;

    private String ownerEmail;

    private LocalDateTime timeCreated =  LocalDateTime.now();
}
