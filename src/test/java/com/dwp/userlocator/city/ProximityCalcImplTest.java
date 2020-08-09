package com.dwp.userlocator.city;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ProximityCalcImplTest {

    private static final double LONDON_LATITUDE = 51.50853;
    private static final double LONDON_LONGITUDE = -0.12574;
    
    private final ProximityCalc proximityCalc = new ProximityCalcImpl();
    
    @Test
    void getMilesBetweenCoordinates_mauriseShieldon_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(34.003135, -117.7228641, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(miles).isCloseTo(5426.30, Assertions.offset(0.005));
    }
    
    @Test
    void getMilesBetweenCoordinates_bendixHalgarth_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(-2.9623869, 104.7399789, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(miles).isCloseTo(7014.85, Assertions.offset(0.005));
    }
    
    @Test
    void getMilesBetweenCoordinates_phyllysHebbs_knownMileToLondon() {
        Double miles = proximityCalc.getMilesBetweenCoordinates(51.5489435, 0.3860497, LONDON_LATITUDE, LONDON_LONGITUDE);
        assertThat(miles).isCloseTo(22.17, Assertions.offset(0.005));
    }
}
