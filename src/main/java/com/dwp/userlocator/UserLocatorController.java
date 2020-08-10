package com.dwp.userlocator;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/locator/users")
@AllArgsConstructor
public class UserLocatorController {
    
    private final UserLocatorService userLocatorService;
    
    private final UserLocatorProperties defaultProperties;

    @RequestMapping(method = RequestMethod.GET)
    public Set<User>  getUsers() {
        return getServiceUsers(defaultProperties.getDefaultCity(), defaultProperties.getDefaultDistance());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = {"city"})
    public Set<User>  getUsers(@RequestParam String city) {
        return getServiceUsers(city, defaultProperties.getDefaultDistance());
    }
    
    @RequestMapping(method = RequestMethod.GET, params = {"distance"})
    public Set<User>  getUsers(@RequestParam double distance) {
        return getServiceUsers(defaultProperties.getDefaultCity(), distance);
    }
    
    @RequestMapping(method = RequestMethod.GET, params = {"city", "distance"})
    public Set<User>  getUsers(@RequestParam String city, @RequestParam double distance) {
        return getServiceUsers(city, distance);
    }
    
    private Set<User> getServiceUsers(String city, double distance) {
        try {
            if (distance < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Distance must be greater than zero");
            }
            return userLocatorService.getUsers(city, distance);
        } catch (UserLocatorException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
