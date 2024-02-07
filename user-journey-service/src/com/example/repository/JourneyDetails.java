package com.example.repository;

import com.example.exceptions.JourneyException;
import com.example.model.journey.Journey;

import java.util.HashMap;
import java.util.Map;

public class JourneyDetails {

    private static JourneyDetails instance = null;
    private static final Object mutex = new Object();
    private static Map<String, Journey> journeys;

    private JourneyDetails() {}

    public static JourneyDetails getInstance() {
        JourneyDetails result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new JourneyDetails();
                    journeys = new HashMap<>();
                }
            }
        }
        return result;
    }

    public void add(final Journey journey) {
        if (journey == null) {
            throw new JourneyException("Journey is null!!");
        }
        synchronized (mutex) {
            journeys.put(journey.getJourneyId(), journey);
        }
    }

    public Journey get(final String journeyId) {
        synchronized (mutex) {
            return journeys.get(journeyId);
        }
    }
}
