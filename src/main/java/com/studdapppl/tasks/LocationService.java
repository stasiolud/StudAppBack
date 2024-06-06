package com.studdapppl.tasks;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    private LocationRepository locationRepository;

    public Location createLocation(String locationName) {
        Location location = locationRepository.insert(new Location(locationName));
        return location;
    }
    public List<Location> allLocations() {
        return locationRepository.findAll();
    }
}