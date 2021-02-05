package com.pravin.oyster.service.impl;

import com.pravin.oyster.exception.InSufficientBalanceException;
import com.pravin.oyster.exception.InvalidTripStateException;
import com.pravin.oyster.model.Card;
import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.Trip;
import com.pravin.oyster.model.TripMode;
import com.pravin.oyster.repository.TripRepository;
import com.pravin.oyster.service.CardService;
import com.pravin.oyster.service.FareService;
import com.pravin.oyster.service.TripService;

import java.util.Objects;

public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final CardService cardService;
    private final FareService fareService;

    public TripServiceImpl(TripRepository tripRepository, CardService cardService, FareService fareService) {
        this.tripRepository = tripRepository;
        this.cardService = cardService;
        this.fareService = fareService;
    }

    @Override
    public void startTrip(TripMode tripMode, String cardId, Station startStation) throws InSufficientBalanceException {
        Objects.requireNonNull(tripMode);
        Objects.requireNonNull(cardId);
        Objects.requireNonNull(startStation);

        double maxFare;
        if (tripMode == TripMode.BUS) {
            maxFare = fareService.getMaxBusFare();
        } else {
            maxFare = fareService.getMaxTubeFare();
        }

        Card card = cardService.retrieveCard(cardId);
        if (card.getBalance() < maxFare) {
            throw new InSufficientBalanceException("Balance less than required min balance");
        }
        cardService.charge(cardId, maxFare);
        tripRepository.addTrip(cardId, new Trip(tripMode, cardId, startStation));
    }

    @Override
    public void endTrip(TripMode tripMode, String cardId, Station endStation) {
        Objects.requireNonNull(tripMode);
        Objects.requireNonNull(cardId);
        Objects.requireNonNull(endStation);

        Trip lastTrip = tripRepository.getLastTrip(cardId).orElseThrow(() -> new InvalidTripStateException("No start trip found for carId: " + cardId));

        if (tripMode == TripMode.TUBE) {
            if (lastTrip.getTripMode() != TripMode.TUBE || lastTrip.getEndStation() != null) {
                throw new InvalidTripStateException("Unable to find unfinished TUBE trip for cardId: " + cardId);
            }
            double charge = fareService.getTubeFare(lastTrip.getStartStation(), endStation);
            cardService.topup(cardId, fareService.getMaxTubeFare());
            cardService.charge(cardId, charge);
        }
        tripRepository.addTrip(cardId, lastTrip.withEndStation(endStation));
    }

}
