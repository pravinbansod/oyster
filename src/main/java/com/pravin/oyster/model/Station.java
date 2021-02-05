package com.pravin.oyster.model;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Station {

    private String name;
    private List<Zone> zones;

    public Station(String name, List<Zone> zones) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(zones);
        this.name = name;
        this.zones = Collections.unmodifiableList(zones);
    }

    public String getName() {
        return name;
    }

    public List<Zone> getZones() {
        return zones;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                '}';
    }
}
