package com.example.model.stage;

import java.util.ArrayList;
import java.util.List;

public class TerminalStage extends Stage {
    public TerminalStage(String name, List<String> conditions) {
        super(name, StageState.TERMINAL, conditions);
    }
}
