package com.dwp.userlocator.city;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.dwp.userlocator.city.ProximityCalc;
import com.dwp.userlocator.city.ProximityCalcImpl;

class ProximityCalcImplTest {

    private static final double LONDON_LATITUDE = 51.50853;
    private static final double LONDON_LONGITUDE = -0.12574;
    
    private final ProximityCalc proximityCalc = new ProximityCalcImpl();
    
    @Test
    void getMilesBetweenCoordinates_mauriseShieldon_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(34.003135, -117.7228641, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(Math.round(miles)).isEqualTo(5426);
    }
    
    @Test
    void getMilesBetweenCoordinates_bendixHalgarth_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(-2.9623869, 104.7399789, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(Math.round(miles)).isEqualTo(7015);
    }
    
    @Test
    void getMilesBetweenCoordinates_phyllysHebbs_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(51.5489435, 0.3860497, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(Math.round(miles)).isEqualTo(22);
    }
}
