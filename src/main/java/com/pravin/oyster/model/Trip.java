package com.pravin.oyster.model;

import java.time.Instant;

public class Trip {

    private final TripMode tripMode;
    private final String cardId;
    private final Station startStation;
    private final Station endStation;
    private final Instant instant;

    public Trip(TripMode tripMode, String cardId, Station startStation) {
        this(tripMode, cardId, startStation, null);
    }

    public Trip(TripMode tripMode, String cardId, Station startStation, Station endStation) {
        this.tripMode = tripMode;
        this.cardId = cardId;
        this.startStation = startStation;
        this.endStation = endStation;
        this.instant = Instant.now();
    }

    public Trip withEndStation(Station endStation) {
       return new Trip(this.tripMode, this.cardId, this.startStation, endStation);
    }

    public TripMode getTripMode() {
        return tripMode;
    }

    public String getCardId() {
        return cardId;
    }

    public Station getStartStation() {
        return startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    @Override
    public String toString() {
        return "\nTrip{" +
                "tripMode=" + tripMode +
                ", cardId='" + cardId + '\'' +
                ", startStation=" + startStation +
                ", endStation=" + endStation +
                ", time=" + instant +
                '}';
    }
}
