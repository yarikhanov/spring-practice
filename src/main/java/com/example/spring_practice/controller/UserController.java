package com.example.spring_practice.controller;

import com.example.spring_practice.dto.AuthRequestDto;
import com.example.spring_practice.dto.AuthResponseDto;
import com.example.spring_practice.dto.UserDto;
import com.example.spring_practice.entity.User;
import com.example.spring_practice.mapper.UserMapper;
import com.example.spring_practice.security.CustomPrincipal;
import com.example.spring_practice.security.SecurityService;
import com.example.spring_practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final SecurityService securityService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public Mono<UserDto> register(@RequestBody UserDto userDto) {
        User user = userMapper.map(userDto);

        return userService.registerUser(user)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssueAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('client:admin')")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}
