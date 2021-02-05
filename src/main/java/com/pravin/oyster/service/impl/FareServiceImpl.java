package com.pravin.oyster.service.impl;

import com.pravin.oyster.model.Station;
import com.pravin.oyster.model.Zone;
import com.pravin.oyster.service.FareDataService;
import com.pravin.oyster.service.FareService;

import java.util.Map;

public class FareServiceImpl implements FareService {

    private final FareDataService fareDataService;

    public FareServiceImpl(FareDataService fareDataService) {
        this.fareDataService = fareDataService;
    }

    @Override
    public double getMaxBusFare() {
        return fareDataService.getMaxBusFare();
    }

    @Override
    public double getMaxTubeFare() {
        return fareDataService.getMaxTubeFare();
    }

    @Override
    public double getTubeFare(Station startStation, Station endStation) {
        double lowestFare = getMaxTubeFare();
        Map<Zone, Map<Zone, Double>> zoneFares = fareDataService.getZoneFares();
        for (Zone sourceZone : startStation.getZones()) {
            for (Zone destZone : endStation.getZones()) {
                double fare = zoneFares.get(sourceZone).get(destZone);
                if (fare < lowestFare) {
                    lowestFare = fare;
                }
            }
        }
        return lowestFare;
    }
}
