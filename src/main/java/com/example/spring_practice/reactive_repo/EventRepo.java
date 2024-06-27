package com.example.spring_practice.reactive_repo;

import com.example.spring_practice.entity.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EventRepo extends R2dbcRepository<Event, Long> {

    Flux<Event> findAllByFile_Id(Long id);
}
