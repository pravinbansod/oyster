package com.pravin.oyster;

import com.pravin.oyster.exception.InSufficientBalanceException;
import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.TripMode;
import com.pravin.oyster.model.Zone;
import com.pravin.oyster.repository.impl.InMemoryCardRepositoryImpl;
import com.pravin.oyster.repository.impl.InMemoryTripRepositoryImpl;
import com.pravin.oyster.service.CardService;
import com.pravin.oyster.service.FareService;
import com.pravin.oyster.service.impl.CardServiceImpl;
import com.pravin.oyster.service.TripService;
import com.pravin.oyster.service.impl.FareServiceImpl;
import com.pravin.oyster.service.impl.TripServiceImpl;

import java.util.Arrays;
import java.util.Collections;

public class DemoClient {
    public static void main(String[] args) throws InSufficientBalanceException {
        final String cardId = "ID1";
        final Station holborn = new Station("Holborn", Arrays.asList(Zone.ZONE_1));
        final Station earlsCourt = new Station("Earl’s Court", Arrays.asList(Zone.ZONE_1, Zone.ZONE_2));
        final Station wimbledon = new Station("Wimbledon", Arrays.asList(Zone.ZONE_3));
        final Station hammersmith = new Station("Hammersmith", Arrays.asList(Zone.ZONE_2));
        final Station chelsea = new Station("Chelsea", Collections.emptyList());

        CardService cardService = new CardServiceImpl(new InMemoryCardRepositoryImpl());
        FareService fareService = new FareServiceImpl(new SampleFareData());
        InMemoryTripRepositoryImpl tripRepository = new InMemoryTripRepositoryImpl();
        TripService tripService = new TripServiceImpl(tripRepository, cardService, fareService);

        // Create Card and TOPUP £30
        cardService.addCard(cardId);
        System.out.println("New Card added, Balance -> " + cardService.retrieveCard(cardId).getBalance());

        cardService.topup(cardId, 30D);
        System.out.println("Balance after £30 topup -> " + cardService.retrieveCard(cardId).getBalance());

        // Tube Holborn to Earl’s Court
        tripService.startTrip(TripMode.TUBE, cardId, holborn);
        tripService.endTrip(TripMode.TUBE, cardId, earlsCourt);
        System.out.println("Balance After 'Tube Holborn to Earl’s Court' -> " + cardService.retrieveCard(cardId).getBalance());

        // Bus from Earl’s Court to Chelsea
        tripService.startTrip(TripMode.BUS, cardId, earlsCourt);
        tripService.endTrip(TripMode.BUS, cardId, chelsea);
        System.out.println("Balance After 'Bus from Earl’s Court to Chelsea' -> " + cardService.retrieveCard(cardId).getBalance());

        // Tube Earl’s court to Hammersmith
        tripService.startTrip(TripMode.TUBE, cardId, earlsCourt);
        tripService.endTrip(TripMode.TUBE, cardId, hammersmith);
        System.out.println("Balance After 'Tube Earl’s court to Hammersmith -> " + cardService.retrieveCard(cardId).getBalance());

        System.out.println("End Balance -> " + cardService.retrieveCard(cardId).getBalance());

        System.out.println("Trip Log -> " + tripRepository.getAllTrips(cardId));

    }
}
