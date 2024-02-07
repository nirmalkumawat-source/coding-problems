package com.example.model.journey;

import com.example.exceptions.JourneyException;
import com.example.model.Payload;
import com.example.model.stage.Stage;
import com.example.repository.UserJourneyStage;
import com.example.service.JourneyEvaluator;
import com.example.service.PerpetualJourneyEvaluator;
import com.example.service.TimeBoundJourneyEvaluator;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class TimeBoundJourney extends Journey{

    public TimeBoundJourney(Date startDate, Date endDate) {
        super(JourneyType.TIME_BOUND, startDate, endDate);
    }

    @Override
    public void evaluate(String userId, Payload payload) {
        JourneyEvaluator evaluator = new TimeBoundJourneyEvaluator(this);

        evaluator.evaluate(userId, payload);
    }
}
