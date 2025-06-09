package com.github.ddehghani.model;

import java.util.Date;
import java.util.List;
public class Meal {
    private Date date;
    private String type;
    private List<Ingredient> ingredients;
    private double calories;
    private double proteins;
    private double carbs;
    private double fats;

    public Meal(Date date, String type, List<Ingredient> ingredients,
                double calories, double proteins, double carbs, double fats) {
        this.date = date;
        this.type = type;
        this.ingredients = ingredients;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
    }

    public Date getDate() {
        return date;
    }


    public String getType() {
        return type;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public double getCalories() {
        return calories;
    }

    public double getProteins() {
        return proteins;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getFats() {
        return fats;
    }
    @Override
    public String toString() {
        return String.format("%1$tA %1$tB %1$td, %1$ty - %2$s", date, type);
    }
}
