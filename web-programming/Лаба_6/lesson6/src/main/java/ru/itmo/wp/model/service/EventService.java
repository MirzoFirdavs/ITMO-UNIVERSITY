package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.event.*;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.EventRepository;
import ru.itmo.wp.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private final EventRepository eventRepository = new EventRepositoryImpl();

    public void addEvent(User user, EventType type) {
        Event event = new Event();
        event.setType(type);
        event.setUserId(user.getId());
        eventRepository.save(event);
    }
}