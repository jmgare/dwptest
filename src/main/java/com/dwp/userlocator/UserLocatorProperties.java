package com.dwp.userlocator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLocatorProperties {

    private String defaultCity;
    
    private double defaultDistance;

    private String userApiBaseUrl;
}