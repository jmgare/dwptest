package com.dwp.userlocator.city;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CityLocation {
    
    @EqualsAndHashCode.Include
    private final String name;
    
    private final double latitude;
    
    private final double longitude;
}