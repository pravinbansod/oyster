package com.pravin.oyster.service;

import com.pravin.oyster.model.Zone;

import java.util.Map;

public interface FareDataService {

    double getMaxBusFare();

    double getMaxTubeFare();

    Map<Zone, Map<Zone, Double>> getZoneFares();
}
