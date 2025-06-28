package com.github.ddehghani.model;

public interface MealSubject {
    void addObserver(MealObserver o);
    void removeObserver(MealObserver o);
    void notifyObservers(Meal m, Nutrition n);
}
