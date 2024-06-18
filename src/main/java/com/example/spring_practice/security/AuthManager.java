package com.example.spring_practice.security;

import com.example.spring_practice.entity.Status;
import com.example.spring_practice.exception.UnauthorizedException;
import com.example.spring_practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {

    private final UserService userService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();
        return userService.getUserById(customPrincipal.getId())
                .filter(user -> user.getStatus().equals(Status.ACTIVE))
                .switchIfEmpty(Mono.error(new UnauthorizedException("unauthorized")))
                .map(user -> authentication);

    }
}
