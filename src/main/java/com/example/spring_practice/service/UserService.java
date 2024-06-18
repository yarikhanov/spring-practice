package com.example.spring_practice.service;

import com.example.spring_practice.entity.Status;
import com.example.spring_practice.entity.User;
import com.example.spring_practice.entity.UserRole;
import com.example.spring_practice.exception.UserNotFoundException;
import com.example.spring_practice.reactive_repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User getById(Long id) {
        return userRepo.findById(id).block();
    }

    public List<User> getAllUsers() {
        return userRepo.findAll().collectList().block();
    }

    public Mono<User> addUser(User user) {
        return userRepo.save(user);
    }

    public Mono<User> updateUser(User user) {
        if (userRepo.existsById(user.getId()).block()) {
            return userRepo.save(user);
        } else {
            throw new UserNotFoundException("User do not exists");
        }
    }

    public void deleteUserById(Long id) {
        userRepo.softDeleteById(id);
    }

    public Mono<User> registerUser(User user) {
        return userRepo.save(
                user.toBuilder()
                        .password(passwordEncoder.encode(user.getPassword()))
                        .userRole(UserRole.USER)
                        .status(Status.ACTIVE)
                        .events(user.getEvents())
                        .build()
        );
    }

    public Mono<User> getUserById(Long id){
        return userRepo.findByIdMono(id);
    }

    public Mono<User> getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
