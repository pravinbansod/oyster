package com.pravin.oyster.repository.impl;

import com.pravin.oyster.model.Card;
import com.pravin.oyster.model.Trip;
import com.pravin.oyster.repository.TripRepository;

import java.util.*;

public class InMemoryTripRepositoryImpl implements TripRepository {

    private Map<String, Deque<Trip>> cardToTripListMap = new HashMap<>();

    @Override
    public void addTrip(String cardId, Trip trip) {
        cardToTripListMap.computeIfAbsent(cardId, (c) -> new LinkedList<>()).add(trip);
    }

    @Override
    public Optional<Trip> getLastTrip(String cardId) {
        Deque<Trip> tripList = cardToTripListMap.get(cardId);
        if (tripList == null) {
            return Optional.empty();
        }
        return Optional.of(tripList.getLast());
    }

    @Override
    public Deque<Trip> getAllTrips(String cardId) {
        return cardToTripListMap.get(cardId);
    }
}
