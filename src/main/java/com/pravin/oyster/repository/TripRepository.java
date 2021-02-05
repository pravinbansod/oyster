package com.pravin.oyster.repository;

import com.pravin.oyster.model.Card;
import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.Trip;

import java.util.Deque;
import java.util.Optional;

public interface TripRepository {

    void addTrip(String cardId, Trip trip);

    Optional<Trip> getLastTrip(String cardId);

    Deque<Trip> getAllTrips(String cardId);

}
