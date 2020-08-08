package com.dwp.userlocator.city;

import com.dwp.userlocator.UserLocatorException;

public interface CityLocationService {

    CityLocation LONDON =  new CityLocation("London", 51.50853, -0.12574);
    
    CityLocation getCityLocation(String name) throws UserLocatorException;

}