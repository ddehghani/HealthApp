package com.github.ddehghani.controller;

import java.util.List;
import java.util.Map;

public class Goal {
    private String type;
    private double intensity;

    public static final List<String> GOAL_TYPES = List.of(
        "dairy consumption",
        "protein intake",
        "calorie intake"
    );

    public static final Map<String, Double> INTENSITY_LEVELS = Map.of(
        "a little bit", 0.1,
        "more than normal", 0.3,
        "a significant amount", 0.5
    );


    public Goal(String type, double intensity) {
        this.type = type;
        this.intensity = intensity;
    }

    public Goal(String type, String intensity) {
        if (!GOAL_TYPES.contains(type)) {
            throw new IllegalArgumentException("Invalid goal type: " + type);
        }
        this.type = type;
        this.intensity = INTENSITY_LEVELS.get(intensity);
    }

    public String getType() {
        return type;
    }

    public double getIntensity() {
        return intensity;
    }
}
