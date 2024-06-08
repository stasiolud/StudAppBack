package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(String eventName, String date, String time) {
        if (!StringUtils.hasText(eventName) || !StringUtils.hasText(date) || !StringUtils.hasText(time)) {
            throw new IllegalArgumentException("Event name, date, and time cannot be null or empty");
        }
        Event event = eventRepository.insert(new Event(eventName, date, time));
        return event;
    }

    public void deleteEvent(ObjectId id) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        eventRepository.deleteById(id);
    }

    public Event updateEvent(ObjectId id, String eventName, String date, String time) {
        if (id == null) {
            throw new IllegalArgumentException("Event ID cannot be null");
        }
        if (!StringUtils.hasText(eventName) || !StringUtils.hasText(date) || !StringUtils.hasText(time)) {
            throw new IllegalArgumentException("Event name, date, and time cannot be null or empty");
        }
        Optional<Event> existingEvent = eventRepository.findById(id);
        if (existingEvent.isEmpty()) {
            throw new RuntimeException("Event not found");
        }
        Event event = existingEvent.get();
        event.setEventName(eventName);
        event.setDate(date);
        event.setTime(time);
        return eventRepository.save(event);
    }

    public List<Event> getEventsByDate(String date) {
        if (!StringUtils.hasText(date)) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        return eventRepository.findByDate(date);
    }
}
