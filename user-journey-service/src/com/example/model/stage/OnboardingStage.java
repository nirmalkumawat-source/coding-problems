package com.example.model.stage;

import java.util.ArrayList;

public class OnboardingStage extends Stage {
    public OnboardingStage(String name) {
        super(name, StageState.ONBOARDING, new ArrayList<>());
    }
}
