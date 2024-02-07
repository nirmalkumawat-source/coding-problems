package com.example.service;

import com.example.model.Payload;

public interface JourneyEvaluator {

    public void evaluate(final String userId, final Payload payload);
}
