package com.github.ddehghani.model;

/**
 * Goal represents a class to store a user's goal 
 */
public class Goal {
    private final String nutrient;
    private final boolean increase;
    private final double intensity;  // total desired amount 

    public Goal(String nutrient, boolean increase, double intensity) {
        this.nutrient = nutrient;
        this.increase = increase;
        this.intensity = intensity;
    }

    public boolean isSatisfiedBy(Nutrition nutrition) {
        if (increase) {
            return nutrition.getNutrient(nutrient) >= intensity;
        } else {
            return nutrition.getNutrient(nutrient) <= intensity;
        }
    }

    public String getNutrient() {
        return nutrient;
    }
    public boolean isIncrease() {
        return increase;
    }
    public double getIntensity() {
        return intensity;
    }
    
}