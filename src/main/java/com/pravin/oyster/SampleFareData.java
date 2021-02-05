package com.pravin.oyster;

import com.pravin.oyster.model.Zone;
import com.pravin.oyster.service.FareDataService;

import java.util.HashMap;
import java.util.Map;

public class SampleFareData implements FareDataService {

    private final Map<Zone, Map<Zone, Double>> zoneFares;
    private final double maxBusFare;
    private final double maxTubeFare;

    public SampleFareData() {
        maxBusFare = 1.80;
        maxTubeFare = 3.2D;

        zoneFares = new HashMap<>();
        final Map<Zone, Double> zone1Fairs = new HashMap<>();
        zone1Fairs.put(Zone.ZONE_1, 2.5);
        zone1Fairs.put(Zone.ZONE_2, 3.0);
        zone1Fairs.put(Zone.ZONE_3, 3.2);
        zoneFares.put(Zone.ZONE_1, zone1Fairs);

        final Map<Zone, Double> zone2Fairs = new HashMap<>();
        zone2Fairs.put(Zone.ZONE_1, 3.0);
        zone2Fairs.put(Zone.ZONE_2, 2.0);
        zone2Fairs.put(Zone.ZONE_3, 2.25);
        zoneFares.put(Zone.ZONE_2, zone2Fairs);

        final Map<Zone, Double> zone3Fairs = new HashMap<>();
        zone3Fairs.put(Zone.ZONE_1, 3.2);
        zone3Fairs.put(Zone.ZONE_2, 2.25);
        zone3Fairs.put(Zone.ZONE_3, 2.0);
        zoneFares.put(Zone.ZONE_3, zone3Fairs);
    }

    @Override
    public double getMaxBusFare() {
        return maxBusFare;
    }

    @Override
    public double getMaxTubeFare() {
        return maxTubeFare;
    }

    @Override
    public Map<Zone, Map<Zone, Double>> getZoneFares() {
        return zoneFares;
    }
}
