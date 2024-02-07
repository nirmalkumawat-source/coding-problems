package com.example.model.journey;

import com.example.model.Payload;
import com.example.service.JourneyEvaluator;
import com.example.service.PerpetualJourneyEvaluator;

public class PerpetualJourney extends Journey {

    public PerpetualJourney() {
        super(JourneyType.PERPETUAL, null, null);
    }

    @Override
    public void evaluate(String userId, Payload payload) {
        JourneyEvaluator evaluator = new PerpetualJourneyEvaluator(this);

        evaluator.evaluate(userId, payload);
    }
}
