package com.dwp.userlocator.userapi;

import java.util.Set;

import com.dwp.userlocator.User;
import com.dwp.userlocator.UserLocatorException;

public interface UserAPIService {

    Set<User> getUsers() throws UserLocatorException;

    Set<User> getUsers(String city) throws UserLocatorException;

}