package com.studdapppl.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LocationController.class)
public class LocationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    @Test
    void testCreateLocation() throws Exception {
        Location location = new Location("Test Location");
        when(locationService.createLocation(anyString())).thenReturn(location);

        mockMvc.perform(post("/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"locationName\":\"Test Location\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"locationName\":\"Test Location\"}"));
    }

    @Test
    void testGetAllLocations() throws Exception {
        Location location = new Location("Test Location");
        when(locationService.allLocations()).thenReturn(Collections.singletonList(location));

        mockMvc.perform(get("/locations"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"locationName\":\"Test Location\"}]"));
    }
}
