package com.example.spring_practice.service;

import com.example.spring_practice.entity.Event;
import com.example.spring_practice.entity.Status;
import com.example.spring_practice.exception.EventNotFoundException;
import com.example.spring_practice.reactive_repo.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public Mono<Event> getById(Long id) {
        return eventRepo.findById(id);
    }

    public Flux<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Mono<Event> addEvent(Event event) {
        return eventRepo.save(event);
    }

    public Mono<Event> updateEvent(Event event) {
        return eventRepo.existsById(event.getId())
                .flatMap(exists -> {
                    if (exists) {
                        return eventRepo.save(event);
                    } else {
                        return Mono.error(new EventNotFoundException("Event do not exists"));
                    }
                });
    }

    public Mono<Void> deleteEventById(Long id) {
        return eventRepo.findById(id)
                .flatMap(event ->
                {
                    if (event != null) {
                        if (event.getStatus().equals(Status.ACTIVE)) {
                            event.setStatus(Status.DELETED);
                            return eventRepo.save(event).then();
                        } else {
                            return Mono.error(new EventNotFoundException("Event do not exists"));
                        }
                    } else {
                        return Mono.error(new EventNotFoundException("Event do not exists"));
                    }
                });
    }
}
