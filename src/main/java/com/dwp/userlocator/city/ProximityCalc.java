package com.dwp.userlocator.city;

public interface ProximityCalc {
    
    double getMilesBetweenCoordinates(double latitude1, double longitude1, double latitude2, double longitude2);
}