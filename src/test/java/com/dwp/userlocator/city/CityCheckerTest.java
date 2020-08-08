package com.dwp.userlocator.city;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dwp.userlocator.User;

@SpringBootTest
public class CityCheckerTest {
    
    @Autowired
    private ProximityCalc proximityCalc;
    
    private User user = new User(554, "Phyllys", "Hebbs", "phebbsfd@umn.edu", "100.89.186.13", 51.5489435, 0.3860497);
    
    @Test
    void testWithinDistanceOfCity_withinDistance() {
        CityChecker cityChecker = new CityChecker(CityLocationService.LONDON, 25.0, proximityCalc);
        assertThat(cityChecker.withinDistanceOfCity(user)).isTrue();
    }
    
    @Test
    void testWithinDistanceOfCity_notWithinDistance() {
        CityChecker cityChecker = new CityChecker(CityLocationService.LONDON, 21.0, proximityCalc);
        assertThat(cityChecker.withinDistanceOfCity(user)).isFalse();
    }
}
