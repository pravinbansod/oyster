package com.pravin.oyster.service;

import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.TripMode;

public interface FareService {

    double getMaxBusFare();

    double getMaxTubeFare();

    double getTubeFare(Station startStation, Station endStation);

}
