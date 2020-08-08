package com.dwp.userlocator.city;

import com.dwp.userlocator.UserLocatorException;

public class CityLocationServiceImpl implements CityLocationService {
    
    @Override
    public CityLocation getCityLocation(String name) throws UserLocatorException {
        if ("London".equalsIgnoreCase(name)) {
            return LONDON; 
        } else {
            throw new UserLocatorException(String.format("City location not found: %s", name));
        }
    }
}
