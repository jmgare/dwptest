package com.dwp.userlocator;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/locator")
public class UserLocatorController {
    
    private final UserLocatorService userLocatorService;
    
    public UserLocatorController(UserLocatorService userLocatorService) {
        this.userLocatorService = userLocatorService;
    }

    @GetMapping("/users")
    public User[] getUsers(
            @RequestParam(name="city", required=false) 
            Optional<String> city,
            @RequestParam(name="distance", required=false)
            Optional<Double> distance
            ) {
        try {
            if (distance.isPresent() && distance.get().doubleValue() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Distance must be greater than zero");
            }
            Set<User> users = userLocatorService.getUsers(city, distance);
            return users.toArray(new User[0]);
        } catch (UserLocatorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
