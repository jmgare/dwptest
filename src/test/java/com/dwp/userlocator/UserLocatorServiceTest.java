package com.dwp.userlocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.dwp.userlocator.city.CityLocationService;
import com.dwp.userlocator.userapi.UserAPIService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootTest
@ActiveProfiles("test")
public class UserLocatorServiceTest {

    @MockBean
    private UserAPIService userAPIService;

    @MockBean
    private CityLocationService cityLocationService;
    
    @Autowired
    private UserLocatorService userLocatorService;
            
    private final User USER_12M = new User(266, "Ancell", "Garnsworthy", "agarnsworthy7d@seattletimes.com", "67.4.69.137", 51.6553959, 0.0572553);
    private final User USER_22M = new User(554, "Phyllys", "Hebbs", "phebbsfd@umn.edu", "100.89.186.13", 51.5489435, 0.3860497);
    private final User USER_41M = new User(322, "Hugo", "Lynd", "hlynd8x@merriam-webster.com", "109.0.153.166", 51.6710832, 0.8078532);
    private final User USER_53M = new User(794, "Katee", "Gopsall", "kgopsallm1@cam.ac.uk", "203.138.133.164", 51.6710832, 1.0978532);
        
    @Test
    void testGetUsers_defaultValues_noUsersFromAPI() throws JsonMappingException, JsonProcessingException {
        when(userAPIService.getUsers()).thenReturn(new HashSet<User>());
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(new HashSet<User>());
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.empty(), Optional.empty());
                
        assertThat(users.isEmpty()).isTrue();
    }
    
    @Test
    void testGetUsers_defaultValuesFromTestProfile_usersWithin_noCityUsers() throws JsonMappingException, JsonProcessingException {
        when(userAPIService.getUsers()).thenReturn(Set.of(USER_12M, USER_22M, USER_41M, USER_53M));
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(new HashSet<User>());
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.empty(), Optional.empty());
                
        assertThat(users.size()).isEqualTo(3);
        
        users.forEach(System.out::println);
    }
    
    @Test
    void testGetUsers_justLondonUsers() throws JsonMappingException, JsonProcessingException {
        Set<User> apiUsers = Set.of(USER_12M, USER_22M, USER_41M, USER_53M);
        when(userAPIService.getUsers()).thenReturn(apiUsers);
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(apiUsers);
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.of(CityLocationService.LONDON.getName()), Optional.of(50.0));
                
        assertThat(users.size()).isEqualTo(4);
        
        users.forEach(System.out::println);
    }
    
    @Test
    void testGetUsers_bothUsersAndLondonUsers() throws JsonMappingException, JsonProcessingException {
        when(userAPIService.getUsers()).thenReturn(Set.of(USER_12M, USER_22M, USER_41M, USER_53M));
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(Set.of(USER_53M));
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.of(CityLocationService.LONDON.getName()), Optional.of(50.0));
                
        assertThat(users.size()).isEqualTo(4);
        
        users.forEach(System.out::println);
    }
    
    @Test
    void testGetUsers_bothUsersAndLondonUsers_distanceAt25Miles() throws JsonMappingException, JsonProcessingException {
        when(userAPIService.getUsers()).thenReturn(Set.of(USER_12M, USER_22M, USER_41M, USER_53M));
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(Set.of(USER_53M));
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.of(CityLocationService.LONDON.getName()), Optional.of(25.0));
                
        assertThat(users.size()).isEqualTo(3);
        assertThat(users.contains(USER_12M)).isTrue();
        assertThat(users.contains(USER_22M)).isTrue();
        assertThat(users.contains(USER_53M)).isTrue();
        
        users.forEach(System.out::println);
    }
    
    @Test
    void testGetUsers_bothUsersAndLondonUsers_distanceAt15Miles() throws JsonMappingException, JsonProcessingException {
        when(userAPIService.getUsers()).thenReturn(Set.of(USER_12M, USER_22M, USER_41M, USER_53M));
        when(userAPIService.getUsers(CityLocationService.LONDON.getName())).thenReturn(Set.of(USER_53M));
        when(cityLocationService.getCityLocation(CityLocationService.LONDON.getName())).thenReturn(CityLocationService.LONDON);
        
        Set<User> users = userLocatorService.getUsers(Optional.of(CityLocationService.LONDON.getName()), Optional.of(15.0));
                
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.contains(USER_12M)).isTrue();
        assertThat(users.contains(USER_53M)).isTrue();
        
        users.forEach(System.out::println);
    }
}