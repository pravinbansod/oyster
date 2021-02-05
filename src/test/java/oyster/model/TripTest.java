package oyster.model;

import com.pravin.oyster.model.*;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class TripTest {

    @Test
    public void canCreateInstanceWithEndStation() {
        Station startStation = new Station("station1", Arrays.asList(Zone.ZONE_1));
        Station endStation = new Station("station2", Arrays.asList(Zone.ZONE_1));
        Trip startTrip = new Trip(TripMode.TUBE, "ID1", startStation);
        Trip endTrip = startTrip.withEndStation(endStation);
        assertEquals("ID1", endTrip.getCardId());
        assertEquals(TripMode.TUBE, endTrip.getTripMode());
        assertEquals(startStation, endTrip.getStartStation());
        assertEquals(endStation, endTrip.getEndStation());
    }

}
