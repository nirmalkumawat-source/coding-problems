package com.example.model.stage;

import java.util.List;

public class IntermediateStage extends Stage {
    public IntermediateStage(String name, List<String> conditions) {
        super(name, StageState.INTERMEDIATE, conditions);
    }
}
