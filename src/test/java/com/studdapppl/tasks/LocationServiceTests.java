package com.studdapppl.tasks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;


public class LocationServiceTests {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLocation() {
        Location location = new Location("Test Location");
        when(locationRepository.insert(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.createLocation("Test Location");
        assertNotNull(createdLocation);
        assertEquals("Test Location", createdLocation.getLocationName());
    }

    @Test
    void testAllLocations() {
        Location location = new Location("Test Location");
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));

        List<Location> locations = locationService.allLocations();
        assertEquals(1, locations.size());
        assertEquals("Test Location", locations.getFirst().getLocationName());
    }
}
