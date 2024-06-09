package com.studdapppl.tasks;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTests {

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @Test
    void createEvent_validInput_createsEvent() {
        String eventName = "Event Name";
        String date = "2023-06-01";
        String time = "10:00";
        Event event = new Event(eventName, date, time);

        when(eventService.createEvent(eventName, date, time)).thenReturn(event);

        ResponseEntity<Event> response = eventController.createEvent(Map.of(
                "eventName", eventName,
                "date", date,
                "time", time
        ));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(event, response.getBody());
        verify(eventService, times(1)).createEvent(eventName, date, time);
    }

    @Test
    void createEvent_invalidInput_returnsBadRequest() {
        ResponseEntity<Event> response = eventController.createEvent(Map.of());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(eventService, never()).createEvent(anyString(), anyString(), anyString());
    }

    @Test
    void deleteEvent_validId_deletesEvent() {
        ObjectId id = new ObjectId();
        doNothing().when(eventService).deleteEvent(id);

        ResponseEntity<Void> response = eventController.deleteEvent(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(eventService, times(1)).deleteEvent(id);
    }

    @Test
    void deleteEvent_nullId_returnsBadRequest() {
        ResponseEntity<Void> response = eventController.deleteEvent(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(eventService, never()).deleteEvent(any());
    }

    @Test
    void updateEvent_validInput_updatesEvent() {
        ObjectId id = new ObjectId();
        String eventName = "New Event Name";
        String date = "2023-06-02";
        String time = "11:00";
        Event event = new Event(id, eventName, date, time);

        when(eventService.updateEvent(id, eventName, date, time)).thenReturn(event);

        ResponseEntity<Event> response = eventController.updateEvent(id, Map.of(
                "eventName", eventName,
                "date", date,
                "time", time
        ));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
        verify(eventService, times(1)).updateEvent(id, eventName, date, time);
    }

    @Test
    void updateEvent_invalidInput_returnsBadRequest() {
        ResponseEntity<Event> response = eventController.updateEvent(null, Map.of());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(eventService, never()).updateEvent(any(), anyString(), anyString(), anyString());
    }

    @Test
    void getEventsByDate_validDate_returnsEvents() {
        String date = "2023-06-01";
        List<Event> events = List.of(new Event("Event Name", date, "10:00"));
        when(eventService.getEventsByDate(date)).thenReturn(events);

        ResponseEntity<List<Event>> response = eventController.getEventsByDate(date);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody());
        verify(eventService, times(1)).getEventsByDate(date);
    }

    @Test
    void getEventsByDate_invalidDate_returnsBadRequest() {
        ResponseEntity<List<Event>> response = eventController.getEventsByDate("");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(eventService, never()).getEventsByDate(anyString());
    }
}
