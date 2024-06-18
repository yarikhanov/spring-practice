package com.example.spring_practice.security;

import com.example.spring_practice.entity.Status;
import com.example.spring_practice.entity.User;
import com.example.spring_practice.exception.AuthException;
import com.example.spring_practice.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final Encoder encoder;

    @Value("${jwt.password.secret}")
    private String secret;
    @Value("${jwt.password.expiration}")
    private Integer expiration;
    @Value("${jwt.password.issuer}")
    private String issuer;

    private TokenDetails generateToken(User user){

        Map<String, Object> claims = new HashMap<>(){{
            put("role", user.getUserRole());
            put("username", user.getUsername());
        }};
        return generateToken(claims,user.getId().toString());
    }
    private TokenDetails generateToken(Map<String, Object> claims, String subject){
        long expirationTime = expiration * 1000L;
        Date expirationDate = new Date(new Date().getTime() + expirationTime);

        return generateToken(expirationDate, claims, subject);
    }

    private TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject){
        Date createdDate = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setExpiration(expirationDate)
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

        return TokenDetails.builder()
                .token(token)
                .expiresAt(expirationDate)
                .issueAt(createdDate)
                .build();
    }

    public Mono<TokenDetails> authenticate(String username, String password) {
        return userService.getUserByUsername(username)
                .flatMap(user -> {
                    if (user.getStatus().equals(Status.DELETED)) {
                        return Mono.error(new AuthException("Account blocked", "USER_ACCOUNT_BLOCKED"));
                    }
                    if (!encoder.matches(password, user.getPassword())) {
                        return Mono.error(new AuthException("Invalid password", "PASSWORD_IS_WRONG"));
                    }

                    return Mono.just(generateToken(user)
                                    .toBuilder()
                            .id(user.getId())
                            .build());
                })
                .switchIfEmpty(Mono.error(new AuthException("Invalid username", "USERNAME_IS_WRONG")));
    }
}
