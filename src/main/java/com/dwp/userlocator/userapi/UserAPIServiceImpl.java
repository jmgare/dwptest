package com.dwp.userlocator.userapi;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.dwp.userlocator.User;
import com.dwp.userlocator.UserLocatorException;
import com.dwp.userlocator.UserLocatorProperties;

public class UserAPIServiceImpl implements UserAPIService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private UserLocatorProperties userLocatorProperties;

    @Override
    public Set<User> getUsers() throws UserLocatorException {
        try {
            String url = String.format("%s/users", userLocatorProperties.getUserApiBaseUrl());
            ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
            User[] users = response.getBody();
            return new HashSet<User>(Arrays.asList(users));
        } catch (RestClientException e) {
            throw new UserLocatorException("Unable to get API users", e);
        }
    }

    @Override
    public Set<User> getUsers(String cityName) throws UserLocatorException {
        try {
            String url = String.format("%s/city/%s/users", userLocatorProperties.getUserApiBaseUrl(), cityName);
            ResponseEntity<User[]> response = restTemplate.getForEntity(url, User[].class);
            User[] users = response.getBody();
            return new HashSet<User>(Arrays.asList(users));
        } catch (RestClientException e) {
            throw new UserLocatorException(String.format("Unable to get API users for city %s", cityName), e);
        }
    }
}
