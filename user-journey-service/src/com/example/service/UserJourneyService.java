package com.example.service;

import com.example.model.journey.Journey;
import com.example.model.Payload;
import com.example.model.stage.Stage;

public interface UserJourneyService {

    public void createJourney(Journey journey);

    public void updateState(String JourneyId, boolean active);

    public Journey getJourney(String journeyId);

    public String evaluate(String userId, Payload payload);

    public Stage getCurrentStage(String userId, String journeyId);

    public boolean isOnboarded(String userId, String journeyId);
}
