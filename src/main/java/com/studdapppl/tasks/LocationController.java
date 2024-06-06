package com.studdapppl.tasks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/locations")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Map<String, String> payload) {
        if (payload == null || !payload.containsKey("locationName") || !StringUtils.hasText(payload.get("locationName"))) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String locationName = payload.get("locationName");
        Location location = locationService.createLocation(locationName);
        return new ResponseEntity<>(location, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.allLocations();
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }
}