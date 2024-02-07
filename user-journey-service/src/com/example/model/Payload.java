package com.example.model;

import com.example.exceptions.KeyAlreadyFoundException;
import com.example.exceptions.KeyNotFoundException;

import java.util.Map;

public class Payload<T> {

    private String userId;

    private String journeyId;

    Map<String, Object> parameters;

    private Payload() {}

    public Payload(String journeyId, String userId, Map<String, Object> params) {
        this.journeyId = journeyId;
        this.userId = userId;
        this.parameters = params;
    }

    public String getUserId() {
        return userId;
    }

    public String getJourneyId() {
        return journeyId;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void addParam(String key, Object val) {
        if (this.parameters.containsKey(key)) {
            throw new KeyAlreadyFoundException(key, "Payload.parameters");
        }
        this.parameters.put(key, val);
    }

    public Object getParam(final String key) {
        if (!this.parameters.containsKey(key)) {
            throw new KeyNotFoundException(key, "Payload.parameters");
        }
        return this.parameters.get(key);
    }
}
