package com.dwp.userlocator.city;

public class ProximityCalcImpl implements ProximityCalc {
    
    public double getMilesBetweenCoordinates(double latitude1, double longitude1, double latitude2, double longitude2) {
        double distance = 
                Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2)) + 
                Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * 
                Math.cos(Math.toRadians(longitude1 - longitude2));
        distance = Math.acos(distance);
        distance = Math.toDegrees(distance);
        return distance * 60 * 1.1515;
    }
}
