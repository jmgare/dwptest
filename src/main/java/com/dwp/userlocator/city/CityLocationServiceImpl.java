package com.dwp.userlocator.city;

import com.dwp.userlocator.UserLocatorException;

/*
 * Considered using a city enum initially but decided to use the CityLocation class instead.
 * Obtaining the CityLocation from implementations of the CityLocationService bean which could
 * be from an API or database source.
 */
public class CityLocationServiceImpl implements CityLocationService {
    
    @Override
    public CityLocation getCityLocation(String name) throws UserLocatorException {
        if (LONDON.getName().equalsIgnoreCase(name)) {
            return LONDON; 
        } else {
            throw new UserLocatorException(String.format("City location not found: %s", name));
        }
    }
}
