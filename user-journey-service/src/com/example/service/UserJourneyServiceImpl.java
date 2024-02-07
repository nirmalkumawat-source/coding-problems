package com.example.service;

import com.example.exceptions.JourneyException;
import com.example.model.Payload;
import com.example.model.journey.Journey;
import com.example.model.stage.Stage;
import com.example.repository.JourneyDetails;
import com.example.repository.UserJourneyStage;

import java.util.List;
import java.util.Map;

public class UserJourneyServiceImpl implements UserJourneyService {
    @Override
    public void createJourney(Journey journey) {
        JourneyDetails.getInstance().add(journey);
        UserJourneyStage.getInstance().addOrUpdate(journey.getJourneyId(), null, null);
    }

    @Override
    public void updateState(String journeyId, boolean active) {
        JourneyDetails.getInstance().get(journeyId).setActive(active);
    }

    @Override
    public Journey getJourney(String journeyId) {
        return JourneyDetails.getInstance().get(journeyId);
    }

    @Override
    public String evaluate(String userId, Payload payload) {
        if (userId == null) {
            throw new JourneyException("UserId is null!!");
        }
        if (payload == null) {
            // nothing to be done.
            return "payload is null";
        }

        final String journeyId = payload.getJourneyId();
        Journey journey = JourneyDetails.getInstance().get(journeyId);

        if (journey == null || !journey.isActive()) {
            return "journey not found!!";
        }

        journey.evaluate(userId, payload);
//
//        Map<String, Stage> userStages = UserJourneyStage.getInstance().getUserStage(journeyId);
//        if (!userStages.containsKey(userId)) {
//            // Onboarding
//            if (evaluateConditions(payload, journey.getStages().get(0).getConditions())) {
//                userStages.put(userId, journey.getStages().get(0));
//            }
//        } else {
//            // Check if user can progress to the next stage
//            Stage currentStage = userStages.get(userId);
//            Stage nextStage = currentStage.getNextStage();
//            if (evaluateConditions(payload, nextStage.getConditions())) {
////                Stage nextStage = currentStage.getNextStage();
//                userStages.put(userId, nextStage);
//            }
//        }
        return "success!";
    }

    @Override
    public Stage getCurrentStage(String userId, String journeyId) {
        if (userId == null || journeyId == null) {
            throw new JourneyException("Input params are null!");
        }
        Map<String, Stage> userStages = UserJourneyStage.getInstance().getUserStage(journeyId);
        if (userStages == null) {
            return null;
        }
        return userStages.get(userId);
    }

    @Override
    public boolean isOnboarded(String userId, String journeyId) {
        if (userId == null || journeyId == null) {
            throw new JourneyException("Input params are null!");
        }
        Map<String, Stage> userStages = UserJourneyStage.getInstance().getUserStage(journeyId);
        if (userStages == null) {
            return false;
        }
        return userStages.get(userId) != null;
    }

    private boolean evaluateConditions(Payload payload, List<String> requiredConditions) {
        for (String condition : requiredConditions) {
            if (!payload.getParameters().containsKey(condition)) {
                return false;
            }
        }
        return true;
    }
}
