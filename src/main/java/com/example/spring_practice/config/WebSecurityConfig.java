package com.example.spring_practice.config;

import com.example.spring_practice.security.AuthManager;
import com.example.spring_practice.security.BearerTokenServerAuthTokenConverter;
import com.example.spring_practice.security.JwtHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;


@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {
    @Value("${jwt.secret}")
    private String secret;

    private final String [] publicRouts = {"/api/v1/register", "/api/v1/login"};

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthManager authenticationManager) {
        return http
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers(HttpMethod.OPTIONS)
                        .permitAll()
                        .pathMatchers(publicRouts)
                        .permitAll()
                        .anyExchange()
                        .authenticated())
                .addFilterAt(bearerAuthFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec.authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))))
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec.accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                                swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))))
                .build();
    }

    private AuthenticationWebFilter bearerAuthFilter(AuthManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new BearerTokenServerAuthTokenConverter(new JwtHandler(secret)));
        authenticationWebFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/**"));
        return authenticationWebFilter;
    }
}
