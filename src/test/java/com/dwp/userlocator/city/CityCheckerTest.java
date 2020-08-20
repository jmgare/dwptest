package com.dwp.userlocator.city;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dwp.userlocator.User;

@SpringBootTest
public class CityCheckerTest {
    
    @MockBean
    private ProximityCalc proximityCalc;
    
    private User user = new User(554, "Phyllys", "Hebbs", "phebbsfd@umn.edu", "100.89.186.13", 51.5489435, 0.3860497);
    
    @Test
    void testWithinDistanceOfCity_equalDistances() {
        when(proximityCalc.getMilesBetweenCoordinates(
                user.getLatitude(), user.getLongitude(), 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude())).thenReturn(new BigDecimal(25.0));
        CityChecker cityChecker = new CityChecker(CityLocationService.LONDON, 25.0, proximityCalc);
        assertThat(cityChecker.withinDistanceOfCity(user)).isTrue();
    }
    
    @Test
    void testWithinDistanceOfCity_withinDistance() {
        when(proximityCalc.getMilesBetweenCoordinates(
                user.getLatitude(), user.getLongitude(), 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude())).thenReturn(new BigDecimal(20.0));
        CityChecker cityChecker = new CityChecker(CityLocationService.LONDON, 25.0, proximityCalc);
        assertThat(cityChecker.withinDistanceOfCity(user)).isTrue();
    }
    
    @Test
    void testWithinDistanceOfCity_notWithinDistance() {
        when(proximityCalc.getMilesBetweenCoordinates(
                user.getLatitude(), user.getLongitude(), 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude())).thenReturn(new BigDecimal(26.0));
        CityChecker cityChecker = new CityChecker(CityLocationService.LONDON, 25.0, proximityCalc);
        assertThat(cityChecker.withinDistanceOfCity(user)).isFalse();
    }
}
