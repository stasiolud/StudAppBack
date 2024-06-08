package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Map<String, String> payload) {
        if (payload == null || payload.get("eventName") == null || payload.get("date") == null || payload.get("time") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(eventService.createEvent(payload.get("eventName"), payload.get("date"), payload.get("time")), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable ObjectId id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable ObjectId id, @RequestBody Map<String, String> payload) {
        if (id == null || payload == null || payload.get("eventName") == null || payload.get("date") == null || payload.get("time") == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Event updatedEvent = eventService.updateEvent(id, payload.get("eventName"), payload.get("date"), payload.get("time"));
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Event>> getEventsByDate(@PathVariable String date) {
        if (!StringUtils.hasText(date)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Event> events = eventService.getEventsByDate(date);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }
}
