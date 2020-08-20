package com.dwp.userlocator.city;

import java.math.BigDecimal;

import org.geotools.referencing.GeodeticCalculator;

public class ProximityCalcGeoToolsImpl implements ProximityCalc {

    private static final double METRES_TO_MILES_MULTIPLIER = 0.000621371;

    public BigDecimal getMilesBetweenCoordinates(double latitude1, double longitude1, double latitude2, double longitude2) {
        GeodeticCalculator calculator = new GeodeticCalculator();
        calculator.setStartingGeographicPoint(longitude1, latitude1);
        calculator.setDestinationGeographicPoint(longitude2, latitude2);
        BigDecimal metres = new BigDecimal(calculator.getOrthodromicDistance());
        BigDecimal miles = metres.multiply(new BigDecimal(METRES_TO_MILES_MULTIPLIER));
        return miles;
    }
}
