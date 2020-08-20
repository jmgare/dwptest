package com.dwp.userlocator.city;

import java.math.BigDecimal;

import com.dwp.userlocator.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityChecker {
    
    private final CityLocation cityLocation;
    
    private final double distance;

    private final ProximityCalc proximityCalc;

    public boolean withinDistanceOfCity(User user) {
        BigDecimal milesBetweenCoordinates = proximityCalc.getMilesBetweenCoordinates(
                user.getLatitude(), user.getLongitude(), cityLocation.getLatitude(), cityLocation.getLongitude());
        return milesBetweenCoordinates.compareTo(new BigDecimal(distance)) <= 0;
    }
}
