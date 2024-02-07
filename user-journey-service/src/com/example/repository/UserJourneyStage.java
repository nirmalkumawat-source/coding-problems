package com.example.repository;

import com.example.exceptions.JourneyException;
import com.example.model.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class UserJourneyStage {
    private static Map<String, Map<String, Stage>> userJourneyStageMap = null;

    private static UserJourneyStage instance = null;
    private static final Object mutex = new Object();

    private UserJourneyStage() {}

    public static UserJourneyStage getInstance() {
        UserJourneyStage result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new UserJourneyStage();
                    userJourneyStageMap = new HashMap<>();
                }
            }
        }
        return result;
    }

    public void addOrUpdate(final String journeyId, final String userId, final Stage stage) {
        if (journeyId == null || journeyId.isEmpty()) {
            throw new JourneyException(String.format("Journey ID [%s] is null!!", journeyId));
        }
        synchronized (mutex) {
            if (!userJourneyStageMap.containsKey(journeyId)) {
                userJourneyStageMap.put(journeyId, new HashMap<>());
            }
            if (userId == null || userId.isEmpty() || stage == null) {
                return;
            }
            Map<String, Stage> userStage = getUserStage(journeyId);
            userStage.put(userId, stage);
            userJourneyStageMap.put(journeyId, userStage);
        }
    }

    public Map<String, Stage> getUserStage(final String journeyId) {
        if (journeyId == null || journeyId.isEmpty()) {
            throw new JourneyException(String.format("Journey with ID [%s] not found!!", journeyId));
        }
        return userJourneyStageMap.get(journeyId);
    }
}
