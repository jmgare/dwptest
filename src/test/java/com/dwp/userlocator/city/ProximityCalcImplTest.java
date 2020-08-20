package com.dwp.userlocator.city;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
/*
 * Values verified via WGS-84 World Geodetic System:
 * https://www.cqsrg.org/tools/GCDistance/
 */
class ProximityCalcImplTest {

    private final ProximityCalc proximityCalc = new ProximityCalcGeoToolsImpl();
    
    @Test
    void getMilesBetweenCoordinates_mauriseShieldon_knownMileToLondon() {
        BigDecimal miles = proximityCalc.getMilesBetweenCoordinates(34.003135, -117.7228641, 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude());        
        assertThat(miles, is(closeTo(new BigDecimal(5439.582), new BigDecimal(0.005))));
    }
    
    @Test
    void getMilesBetweenCoordinates_bendixHalgarth_knownMileToLondon() {
        BigDecimal miles = proximityCalc.getMilesBetweenCoordinates(-2.9623869, 104.7399789, 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude());        
        assertThat(miles, is(closeTo(new BigDecimal(7016.575), new BigDecimal(0.005))));
    }
    
    @Test
    void getMilesBetweenCoordinates_phyllysHebbs_knownMileToLondon() {
        BigDecimal miles = proximityCalc.getMilesBetweenCoordinates(51.5489435, 0.3860497, 
                CityLocationService.LONDON.getLatitude(), CityLocationService.LONDON.getLongitude());        
        assertThat(miles, is(closeTo(new BigDecimal(22.245), new BigDecimal(0.005))));
    }
    
    @Test
    void getMilesBetweenCoordinates_knownDistance() {
        BigDecimal miles = proximityCalc.getMilesBetweenCoordinates(-24.875975, 152.352298, -24.993289, 151.960336);
        assertThat(miles, is(closeTo(new BigDecimal(25.89), new BigDecimal(0.005))));
    }
}
