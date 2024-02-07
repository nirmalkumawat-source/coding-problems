package com.example.service;

import com.example.exceptions.JourneyException;
import com.example.model.Payload;
import com.example.model.journey.PerpetualJourney;
import com.example.model.journey.TimeBoundJourney;
import com.example.model.stage.Stage;
import com.example.repository.UserJourneyStage;

import java.util.List;
import java.util.Map;

public class PerpetualJourneyEvaluator implements JourneyEvaluator{

    private PerpetualJourney journey = null;

    public PerpetualJourneyEvaluator(PerpetualJourney journey) {
        this.journey = journey;
    }

    @Override
    public void evaluate(final String userId, final Payload payload) {
        if (!journey.isActive()) {
            throw new JourneyException(String.format("Journey with id %s is not active", journey.getJourneyId()));
        }

        Map<String, Stage> userStages = UserJourneyStage.getInstance().getUserStage(journey.getJourneyId());
        if (!userStages.containsKey(userId)) {
            // Onboarding
            if (evaluateConditions(payload, journey.getStages().get(0).getConditions())) {
                userStages.put(userId, journey.getStages().get(0));
                // send an event to user
            }
        } else {
            // Check if user can progress to the next stage
            Stage currentStage = userStages.get(userId);
            Stage nextStage = currentStage.getNextStage();
            if (evaluateConditions(payload, nextStage.getConditions())) {
//                Stage nextStage = currentStage.getNextStage();
                userStages.put(userId, nextStage);
            }
        }
        \
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
