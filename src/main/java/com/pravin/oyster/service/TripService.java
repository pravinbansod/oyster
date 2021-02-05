package com.pravin.oyster.service;

import com.pravin.oyster.exception.InSufficientBalanceException;
import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.TripMode;

public interface TripService {
    void startTrip(TripMode tripMode, String cardId, Station station) throws InSufficientBalanceException;

    void endTrip(TripMode tripMode, String cardId, Station station);
}
