package com.example.model.journey;

import com.example.model.Payload;
import com.example.model.stage.Stage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class Journey {
    String journeyId;
    JourneyType journeyType;
    Date startDate;
    Date endDate;

    public void setActive(boolean active) {
        this.active = active;
    }

    boolean active;

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }

    List<Stage> stages;

    public Journey(JourneyType journeyType, Date startDate, Date endDate) {
        this.journeyId = UUID.randomUUID().toString();
        this.journeyType = journeyType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = false;
        this.stages = new ArrayList<>();
    }

    public String getJourneyId() {
        return journeyId;
    }

    public JourneyType getJourneyType() {
        return journeyType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void evaluate(String userId, Payload payload) {
        // do nothing.
    }
}
