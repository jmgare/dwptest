package com.dwp.userlocator.city;

import java.math.BigDecimal;

public interface ProximityCalc {
    
    BigDecimal getMilesBetweenCoordinates(double latitude1, double longitude1, double latitude2, double longitude2);
}