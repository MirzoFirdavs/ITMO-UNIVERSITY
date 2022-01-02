package ru.itmo.wp.model.domain.event;

import ru.itmo.wp.model.domain.Entity;

public class Event extends Entity {
    private long userId;
    private EventType type;

    public long getUserId() {
        return userId;
    }

    public EventType getType() {
        return type;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}