package com.example.spring_practice.controller;

import com.example.spring_practice.entity.Event;
import com.example.spring_practice.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/event")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @PreAuthorize("hasAuthority('client:moderator')")
    public Flux<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('client:user')")
    public Mono<Event> getEventById(@PathVariable Long id) {
        return eventService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('client:moderator')")
    public void deleteEventById(@PathVariable Long id){
        eventService.deleteEventById(id);
    }
}
