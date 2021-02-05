package oyster.service.impl;

import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.Zone;
import com.pravin.oyster.service.FareDataService;
import com.pravin.oyster.service.impl.FareServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FareServiceImplTest {

    private FareDataService fareDataService;
    private FareServiceImpl underTest;

    @Before
    public void setup() {
        fareDataService = new TestFareData();
        underTest = new FareServiceImpl(fareDataService);
    }

    @Test
    public void canGetMaxBusFare() {
        assertEquals(1.8D, underTest.getMaxBusFare(), 0D);
    }

    @Test
    public void canGetMaxTubeFare() {
        assertEquals(3.2D, underTest.getMaxTubeFare(), 0D);
    }

    @Test
    public void getTubeFareReturnsValidFareForStationsWithSingleZone() {
        final Station startStation = new Station("startStation", Arrays.asList(Zone.ZONE_1));
        final Station endStation = new Station("endStation", Arrays.asList(Zone.ZONE_3));
        assertEquals(3.2D, underTest.getTubeFare(startStation, endStation), 0D);
    }

    @Test
    public void getTubeFareReturnsLowestFareWhenStartStationInMultipleZone() {
        final Station startStation = new Station("startStation", Arrays.asList(Zone.ZONE_1, Zone.ZONE_3));
        final Station endStation = new Station("endStation", Arrays.asList(Zone.ZONE_3));
        assertEquals(2.0D, underTest.getTubeFare(startStation, endStation), 0D);
    }

    @Test
    public void getTubeFareReturnsLowestFareWhenEndStationInMultipleZone() {
        final Station startStation = new Station("startStation", Arrays.asList(Zone.ZONE_1));
        final Station endStation = new Station("endStation", Arrays.asList(Zone.ZONE_2, Zone.ZONE_3));
        assertEquals(3.0D, underTest.getTubeFare(startStation, endStation), 0D);
    }

    class TestFareData implements FareDataService {

        private final Map<Zone, Map<Zone, Double>> zoneFares;
        private final double maxBusFare;
        private final double maxTubeFare;

        public TestFareData() {
            maxBusFare = 1.80;
            maxTubeFare = 3.2D;

            zoneFares = new HashMap<>();
            final Map<Zone, Double> zone1Fairs = new HashMap<>();
            zone1Fairs.put(Zone.ZONE_1, 2.5);
            zone1Fairs.put(Zone.ZONE_2, 3.0);
            zone1Fairs.put(Zone.ZONE_3, 3.2);
            zoneFares.put(Zone.ZONE_1, zone1Fairs);

            final Map<Zone, Double> zone2Fairs = new HashMap<>();
            zone2Fairs.put(Zone.ZONE_1, 3.0);
            zone2Fairs.put(Zone.ZONE_2, 2.0);
            zone2Fairs.put(Zone.ZONE_3, 2.25);
            zoneFares.put(Zone.ZONE_2, zone2Fairs);

            final Map<Zone, Double> zone3Fairs = new HashMap<>();
            zone3Fairs.put(Zone.ZONE_1, 3.2);
            zone3Fairs.put(Zone.ZONE_2, 2.25);
            zone3Fairs.put(Zone.ZONE_3, 2.0);
            zoneFares.put(Zone.ZONE_3, zone3Fairs);
        }

        @Override
        public double getMaxBusFare() {
            return maxBusFare;
        }

        @Override
        public double getMaxTubeFare() {
            return maxTubeFare;
        }

        @Override
        public Map<Zone, Map<Zone, Double>> getZoneFares() {
            return zoneFares;
        }
    }

}
