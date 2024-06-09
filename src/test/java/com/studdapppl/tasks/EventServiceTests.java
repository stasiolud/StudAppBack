package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTests {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_validInput_createsEvent() {
        String eventName = "Event Name";
        String date = "2023-06-01";
        String time = "10:00";

        Event event = new Event(eventName, date, time);
        when(eventRepository.insert(any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(eventName, date, time);

        assertEquals(eventName, createdEvent.getEventName());
        assertEquals(date, createdEvent.getDate());
        assertEquals(time, createdEvent.getTime());
        verify(eventRepository, times(1)).insert(any(Event.class));
    }

    @Test
    void createEvent_invalidInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.createEvent("", "2023-06-01", "10:00");
        });
    }

    @Test
    void deleteEvent_validId_deletesEvent() {
        ObjectId id = new ObjectId();
        doNothing().when(eventRepository).deleteById(id);

        eventService.deleteEvent(id);

        verify(eventRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteEvent_nullId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.deleteEvent(null);
        });
    }

    @Test
    void updateEvent_validInput_updatesEvent() {
        ObjectId id = new ObjectId();
        Event event = new Event(id, "Old Event Name", "2023-06-01", "10:00");
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(id, "New Event Name", "2023-06-02", "11:00");

        assertEquals("New Event Name", updatedEvent.getEventName());
        assertEquals("2023-06-02", updatedEvent.getDate());
        assertEquals("11:00", updatedEvent.getTime());
        verify(eventRepository, times(1)).findById(id);
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void updateEvent_invalidInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.updateEvent(null, "Event Name", "2023-06-01", "10:00");
        });
    }

    @Test
    void getEventsByDate_validDate_returnsEvents() {
        String date = "2023-06-01";
        List<Event> events = List.of(new Event("Event Name", date, "10:00"));
        when(eventRepository.findByDate(date)).thenReturn(events);

        List<Event> result = eventService.getEventsByDate(date);

        assertEquals(events, result);
        verify(eventRepository, times(1)).findByDate(date);
    }

    @Test
    void getEventsByDate_invalidDate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.getEventsByDate("");
        });
    }
}
