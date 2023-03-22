package com.musalasoft.model;

import java.io.Serializable;

public enum State implements Serializable {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    DELIVERED("DELIVERED"),
    RETURNING("RETURNING");
    private final String value;

    private State(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static State fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (State state : values()) {
            if (state.value().equalsIgnoreCase(value)) {
                return state;
            }
        }
        throw new IllegalStateException("No enum for " + value);
    }
}
