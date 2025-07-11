package com.github.ddehghani.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Model {
    // UC 1
    void setProfile(UserProfile profile);
    UserProfile getProfile(String email);
    void updateProfile(UserProfile profile);
    void deleteProfile(String email);

    // UC 2
    void addMeal(Meal meal, String email);
    List<Meal> getMeals(String email);
    String getNutrientUnit(String nutrient);
    List<String> getFoodNames(); // + getFoodItemsList(): List <String>

    // UC 3
    List<Meal> getMealsByDate(String email, Date date);
    List<Meal> getMealsByTimeFrame(String email, Date begin, Date end);
    List<FoodItem> getSwappedFoodOptions(Meal originalMeal,  int foodItemIndex, List<Goal> goals); // new based on prof's new request
    List<String> getNutrientNames();
    Nutrition getMealNutrtionalValue(Meal originalMeal); // new
    Nutrition getFoodItemNutrtionalValue(FoodItem foodItem); // + getNutritionalValue(foodItem: FoodItem): Nutrition
    String getFoodCategory(String foodName); // + getCategory(foodItem: String): String

    
    List<String> getFoodNamesWithSameFoodCategoryAs(String foodName); // + getAlternativeFoodItemsByCategory(category: String): List<String>
    List<String> getAvailableUnits(String foodName); // new can return empty set
   
    Map<String, Double> getDailyRecommendationsFromCFG();
}