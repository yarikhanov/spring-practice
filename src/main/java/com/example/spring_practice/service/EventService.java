package com.example.spring_practice.service;

import com.example.spring_practice.entity.Event;
import com.example.spring_practice.exception.EventNotFoundException;
import com.example.spring_practice.repository.EventRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;

    public Event getById(Long id) {
        return eventRepo.findById(id).orElseThrow(() -> new EventNotFoundException("Event not found"));
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event addEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event updateEvent(Event event) {
        if (eventRepo.existsById(event.getId())) {
            return eventRepo.save(event);
        } else {
            throw new EventNotFoundException("Event do not exists");
        }
    }

    public void deleteEventById(Long id) {
        eventRepo.softDeleteById(id);
    }
}
