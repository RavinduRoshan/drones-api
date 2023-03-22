package com.musalasoft.model;

import java.io.Serializable;

public enum Model implements Serializable {
    LIGHTWEIGHT("Lightweight"),
    MIDDLEWEIGHT("Middleweight"),
    CRUISEWEIGHT("Cruiserweight"),
    HEAVYWEIGHT("Heavyweight");
    private final String value;

    private Model(String value){
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static Model fromValue(String value) {
        if (value == null) {
            return null;
        }
        for (Model model : values()) {
            if (model.value().equalsIgnoreCase(value)) {
                return model;
            }
        }
        throw new IllegalStateException("No enum for " + value);
    }

}
