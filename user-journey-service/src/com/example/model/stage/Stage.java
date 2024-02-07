package com.example.model.stage;

import java.util.List;

public abstract class Stage {
    String name;
    StageState stageState;

    public Stage getNextStage() {
        return nextStage;
    }

    public void setNextStage(Stage nextStage) {
        this.nextStage = nextStage;
    }

    public void setName(String name) {
        this.name = name;
    }

    Stage nextStage;
    List<String> conditions;

    public Stage(String name, StageState state, List<String> conditions) {
        this.name = name;
        this.stageState = state;
        this.conditions = conditions;
        this.nextStage = null;
//        this.stageIndex = index;
    }

    public String getName() {
        return name;
    }

    public StageState getStageState() {
        return stageState;
    }

    public List<String> getConditions() {
        return conditions;
    }


}
