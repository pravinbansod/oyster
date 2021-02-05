package oyster.service.impl;

import com.pravin.oyster.exception.InSufficientBalanceException;
import com.pravin.oyster.exception.InvalidTripStateException;
import com.pravin.oyster.model.*;
import com.pravin.oyster.repository.TripRepository;
import com.pravin.oyster.repository.impl.InMemoryTripRepositoryImpl;
import com.pravin.oyster.service.CardService;
import com.pravin.oyster.service.FareService;
import com.pravin.oyster.service.impl.TripServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Deque;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TripServiceImplTest {

    private final Station startStation = new Station("station1", Arrays.asList(Zone.ZONE_1));
    private final Station endStation = new Station("station1", Arrays.asList(Zone.ZONE_2));
    private TripRepository tripRepository;
    private TripServiceImpl underTest;
    private CardService cardService = Mockito.mock(CardService.class);
    private FareService fareService = Mockito.mock(FareService.class);

    @Before
    public void setup() {
        tripRepository = new InMemoryTripRepositoryImpl();
        cardService = Mockito.mock(CardService.class);
        fareService = Mockito.mock(FareService.class);
        underTest = new TripServiceImpl(tripRepository, cardService, fareService);
    }

    @Test(expected = InSufficientBalanceException.class)
    public void startTripThrowsInSufficientBalanceExceptionWhenInsufficientBalance() throws Exception {
        Card card = new Card("ID1", 3.0D);
        when(cardService.retrieveCard("ID1")).thenReturn(card);
        when(fareService.getMaxTubeFare()).thenReturn(3.2D);
        underTest.startTrip(TripMode.TUBE, "ID1", startStation);
    }

    @Test
    public void canStartTubeTrip() throws Exception {
        Card card = new Card("ID1", 10D);
        when(cardService.retrieveCard("ID1")).thenReturn(card);
        when(fareService.getMaxTubeFare()).thenReturn(3.2D);

        underTest.startTrip(TripMode.TUBE, "ID1", startStation);

        Deque<Trip> trips = tripRepository.getAllTrips("ID1");
        Trip trip = trips.getLast();
        assertEquals("ID1", trip.getCardId());
        assertEquals(TripMode.TUBE, trip.getTripMode());
        assertEquals(startStation, trip.getStartStation());
        assertNull(trip.getEndStation());
        verify(cardService).retrieveCard("ID1");
        verify(fareService).getMaxTubeFare();
        verify(cardService).charge("ID1", 3.2D);
    }

    @Test
    public void canStartBusTrip() throws Exception {
        Card card = new Card("ID1", 10D);
        when(cardService.retrieveCard("ID1")).thenReturn(card);
        when(fareService.getMaxBusFare()).thenReturn(1.8D);

        underTest.startTrip(TripMode.BUS, "ID1", startStation);

        Deque<Trip> trips = tripRepository.getAllTrips("ID1");
        Trip trip = trips.getLast();
        assertEquals("ID1", trip.getCardId());
        assertEquals(TripMode.BUS, trip.getTripMode());
        assertEquals(startStation, trip.getStartStation());
        assertNull(trip.getEndStation());
        verify(cardService).retrieveCard("ID1");
        verify(fareService).getMaxBusFare();
        verify(cardService).charge("ID1", 1.8D);
    }

    @Test
    public void canEndBusTrip() {
        tripRepository.addTrip("ID1", new Trip(TripMode.BUS, "ID1", startStation));

        underTest.endTrip(TripMode.BUS, "ID1", endStation);

        Deque<Trip> trips = tripRepository.getAllTrips("ID1");
        Trip trip = trips.getLast();
        assertEquals("ID1", trip.getCardId());
        assertEquals(TripMode.BUS, trip.getTripMode());
        assertEquals(startStation, trip.getStartStation());
        assertEquals(endStation, trip.getEndStation());
        verify(fareService, never()).getMaxBusFare();
        verify(cardService, never()).topup(anyString(), anyDouble());
        verify(cardService, never()).charge(anyString(), anyDouble());
    }

    @Test
    public void canEndTubeTrip() {
        when(fareService.getMaxTubeFare()).thenReturn(3.2D);
        when(fareService.getTubeFare(startStation, endStation)).thenReturn(2.5D);
        tripRepository.addTrip("ID1", new Trip(TripMode.TUBE, "ID1", startStation));

        underTest.endTrip(TripMode.TUBE, "ID1", endStation);

        Deque<Trip> trips = tripRepository.getAllTrips("ID1");
        Trip trip = trips.getLast();
        assertEquals("ID1", trip.getCardId());
        assertEquals(TripMode.TUBE, trip.getTripMode());
        assertEquals(startStation, trip.getStartStation());
        assertEquals(endStation, trip.getEndStation());
        verify(fareService).getMaxTubeFare();
        verify(fareService).getTubeFare(startStation, endStation);
        verify(cardService).topup("ID1", 3.2D);
        verify(cardService).charge("ID1", 2.5D);
    }

    @Test(expected = InvalidTripStateException.class)
    public void endTripThrowsExceptionWhenTripLogIsEmpty() {
        underTest.endTrip(TripMode.BUS, "ID1", endStation);
    }

    @Test(expected = InvalidTripStateException.class)
    public void endTubeTripThrowsExceptionWhenUnfinishedTubeTripNotFound() {
        tripRepository.addTrip("ID1", new Trip(TripMode.TUBE, "ID1", startStation).withEndStation(endStation));
        underTest.endTrip(TripMode.TUBE, "ID1", endStation);
    }

    @Test(expected = InvalidTripStateException.class)
    public void endTubeTripThrowsExceptionWhenTubeStartTripNotFound() {
        tripRepository.addTrip("ID1", new Trip(TripMode.BUS, "ID1", startStation));
        underTest.endTrip(TripMode.TUBE, "ID1", endStation);
    }
}
