package com.github.ddehghani.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.List;

public interface Database {
    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    boolean registerUser(User user);
    Optional<User> authenticateUser(String email, String password);
    boolean updateUserProfile(User user);
    boolean addMeal(Meal meal, String email);
    String[] getIngredients(); // Returns an array of all possible ingredient names
    List<Meal> getMeals(String email); // Returns meals for a specific user
    List<Meal> getMeals(String email, String startDate, String endDate);

    
}