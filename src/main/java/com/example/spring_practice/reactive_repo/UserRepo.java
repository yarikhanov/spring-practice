package com.example.spring_practice.reactive_repo;

import com.example.spring_practice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserRepo extends R2dbcRepository<User, Long> {

    @Query("update User u set u.status = 'DELETED' where u.id = :id")
    @Modifying
    void softDeleteById(@Param("id") Long id);

    Mono<User> findByUsername(String username);

    Mono<User> findByIdMono (Long id);

}
