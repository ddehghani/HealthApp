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
    List<Meal> getMealsByUser(String email); // Returns meals for a specific user
    List<Meal> getMealsByUserAndPeriod(String email, String startDate, String endDate);
    
    // for future features
    String getFoodCategory(String foodName); // Returns the category of a food item
    List<String> getFoodItemsByCategory(String category); // Returns a list of food items matching the nam
    NutritionalInfo getNutritionalInfo(FoodItem foodName); // Returns nutritional info for a food item
    NutritionalInfo getRecommendedDailyNutrition(); // Returns recommended daily nutrition values
    String getPercentageOfFoodGroup(); // From CFG
}