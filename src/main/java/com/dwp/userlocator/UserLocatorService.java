package com.dwp.userlocator;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dwp.userlocator.city.CityChecker;
import com.dwp.userlocator.city.CityLocation;
import com.dwp.userlocator.city.CityLocationService;
import com.dwp.userlocator.city.ProximityCalc;
import com.dwp.userlocator.userapi.UserAPIService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserLocatorService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserLocatorService.class);
    
    private final UserAPIService userAPIService;

    private final CityLocationService cityLocationService;

    private final ProximityCalc proximityCalc;
    
    public Set<User> getUsers(String city, double distance) {
        logger.info(String.format("Get users for %s/%s", city, distance));
        try {
            final Set<User> usersWithinCity = userAPIService.getUsers(city);            
            Predicate<User> containedInCityUsers = usersWithinCity::contains;

            CityLocation cityLocation = cityLocationService.getCityLocation(city);
            CityChecker cityChecker = new CityChecker(cityLocation, distance, proximityCalc);

            Set<User> users = userAPIService.getUsers();
            Set<User> usersWithinProximity = users.stream().
                filter(containedInCityUsers.negate()).
                filter(cityChecker::withinDistanceOfCity).
                collect(Collectors.toSet());
            
            return Stream.of(usersWithinCity, usersWithinProximity).flatMap(Set::stream).collect(Collectors.toSet());
        } catch (Exception e) {
            String message = String.format("Unable to get users for %s/%s", city, distance);
            logger.error(message ,e);
            throw new UserLocatorException(message, e);
        }
    }
}
