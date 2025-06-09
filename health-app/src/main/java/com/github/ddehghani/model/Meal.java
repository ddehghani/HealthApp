package com.github.ddehghani.model;

import java.util.Date;
import java.util.List;

public class Meal {
    private Date date;
    private String type;
    private List<String> ingredients;
    private List<String> quantities;
    private double calories;
    private double proteins;
    private double carbs;
    private double vitamins;
    private double others;

    public Meal(Date date, String type, List<String> ingredients, List<String> quantities,
                double calories, double proteins, double carbs, double vitamins, double others) {
        this.date = date;
        this.type = type;
        this.ingredients = ingredients;
        this.quantities = quantities;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.vitamins = vitamins;
        this.others = others;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<String> quantities) {
        this.quantities = quantities;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getVitamins() {
        return vitamins;
    }

    public void setVitamins(double vitamins) {
        this.vitamins = vitamins;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }

    @Override
    public String toString() {
        return String.format("%s – %s (%s)", date.toString(), type, String.join(", ", ingredients));
    }
}
