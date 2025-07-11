package com.github.ddehghani.controller;

import com.github.ddehghani.model.*;
import com.github.ddehghani.view.*;

import java.util.*;
import java.util.function.Function;

public class MainController {
    private final MainView mainView;
    private final Model db;
    private UserProfile currentUser;

    public MainController() {
        mainView = new MainView();
        db = ConcreteModel.getInstance();
        registerEventHandlers();

        mainView.showCard(MainView.LOGIN); // Start with the login panel
        mainView.setVisible(true);
    }

    private void registerEventHandlers() {
        mainView.getLoginPanel().addLoginButtonListener(e -> handleLogin());
        mainView.getLoginPanel().addSwitchToRegisterListener(e -> {
            mainView.getLoginPanel().clearFields(); // Clear fields on switch
            mainView.showCard(MainView.REGISTER);
        });
        mainView.getRegisterPanel().addSwitchToLoginListener(e -> {
            mainView.getRegisterPanel().clearFields(); // Clear fields on switch
            mainView.showCard(MainView.LOGIN);
        });
        mainView.getRegisterPanel().addRegisterButtonListener(e -> handleRegister());
        mainView.getHomePanel().addLogMealListener(e -> {
            List<String> foodNames = db.getFoodNames();
            foodNames.addFirst("<pick one>");
            mainView.getLogMealPanel().setIngredients(foodNames.toArray(new String[]{}));

            List<Meal> meals = db.getMeals(currentUser.getEmail());
            List<Nutrition> nutritionalValues = new ArrayList<>();
            for (Meal meal : meals) {
                nutritionalValues.add(db.getMealNutrtionalValue(meal));
            }

            mainView.getLogMealPanel().setInitialMealsAndNutritions(meals, nutritionalValues);
            mainView.showCard(MainView.LOG_MEAL);
        });

        mainView.getHomePanel().addChangeProfileListener(e -> {
            mainView.getEditProfilePanel().setUserDetails(currentUser);
            mainView.showCard(MainView.EDIT_PROFILE);
        });

        mainView.getHomePanel().addFoodReplacementListener(e -> {
            List<String> nutrients = db.getNutrientNames();
            String[] nutrientOptions = makeComboboxOptions(nutrients, el -> el);
            mainView.getFoodReplacementPanel().setNutrientsNames(nutrientOptions);
            List<Meal> meals = db.getMeals(currentUser.getEmail());
            List<Nutrition> nutritionalValues = new ArrayList<>();
            for (Meal meal : meals) {
                nutritionalValues.add(db.getMealNutrtionalValue(meal));
            }
            mainView.getFoodReplacementPanel().setInitialMealsAndNutritions(meals, nutritionalValues);
            mainView.showCard(MainView.FOOD_REPLACEMENT);
        });
        mainView.getHomePanel().addLogoutListener(e -> {
            currentUser = null;
            mainView.showMessage("You have been logged out.");
            mainView.showCard(MainView.LOGIN);
        });
        
        mainView.getLogMealPanel().addBackButtonListener(e -> {
            mainView.getLogMealPanel().clearFields();
            mainView.showCard(MainView.HOME);
        });

        mainView.getLogMealPanel().addIngredientSelectionListener(e -> {
            int index = Integer.parseInt(e.getActionCommand());
            List<String> availableUnits = db.getAvailableUnits( mainView.getLogMealPanel().getSelectedIngredients().get(index)[0]);
            String[] units = availableUnits.isEmpty() ? new String[] {"unit"} : availableUnits.toArray(new String[] {});
            mainView.getLogMealPanel().setUnits(units, index);
        });
        
        mainView.getLogMealPanel().addAddMealButtonListener(e -> {
            LogMealPanel addMealPanel = mainView.getLogMealPanel();
            Date date = addMealPanel.getSelectedDate();
            String type = addMealPanel.getSelectedMealType();
            List<String[]> ingredients = addMealPanel.getSelectedIngredients();

            if (date == null || type == null || ingredients.isEmpty()) {
                mainView.showError("Please fill in all fields.");
                return;
            }
            List<FoodItem> foodItems = new ArrayList<>();
            try {
                for (String[] ingredient : ingredients) {
                    foodItems.add(new FoodItem(
                        ingredient[0], Double.parseDouble(ingredient[1]), ingredient[2]
                    ));
                }
            } catch (NumberFormatException exc) {
                mainView.showError("All ingredients must have a name and quantity and unit.");
                return;
            }
            
            
            Meal meal = new Meal(date, foodItems, type);
            try {
                db.addMeal(meal, currentUser.getEmail());
                mainView.showMessage("Meal added successfully!");
                addMealPanel.clearInputFields(); // Clear fields after adding meal
            } catch (Exception ex) {
                ex.printStackTrace();
                mainView.showError("Failed to add meal.");
            }
        });
        mainView.getEditProfilePanel().addSaveProfileListener(e -> handleSaveProfile());
        mainView.getEditProfilePanel().addBackListener(e -> mainView.showCard(MainView.HOME));
        mainView.getEditProfilePanel().addDeleteListener(e -> {
            if (mainView.showConfirmDialog("Are you sure you want to delete your profile?")) {
                db.deleteProfile(mainView.getEditProfilePanel().getEmail());
                mainView.showCard(MainView.LOGIN);
            }
        });

        mainView.getFoodReplacementPanel().addMealTypeComboBoxListener(e -> {
            Date date = mainView.getFoodReplacementPanel().getSelectedDate();
            List<Meal> meals = db.getMealsByDate(currentUser.getEmail(), date );
            String mealType = mainView.getFoodReplacementPanel().getSelectedMealType();
            for (Meal m : meals) {
                if (m.getType().equals(mealType)) {
                    String[] foodItemOptions = makeComboboxOptions(m.getFoodItems(), FoodItem::toString);
                    mainView.getFoodReplacementPanel().setFoodItemNames(foodItemOptions);
                }
            }

        });

         mainView.getFoodReplacementPanel().addDateChangeListener(e -> {
            Date date = mainView.getFoodReplacementPanel().getSelectedDate();
            List<Meal> meals = db.getMealsByDate(currentUser.getEmail(), date );
            String[] mealOptions = makeComboboxOptions(meals, (element -> element.getType()));
            mainView.getFoodReplacementPanel().setMealTypes(mealOptions);
         });

        mainView.getFoodReplacementPanel().setNutrientComboBoxesListener(e -> {
            int index = Integer.parseInt(e.getActionCommand());
            String selectedNutrient = mainView.getFoodReplacementPanel().getSelectedNutrients().get(index);
            String unit = db.getNutrientUnit(selectedNutrient);
            String[] unitOptions = new String[] {"%", unit};
            mainView.getFoodReplacementPanel().setIntensityUnits(unitOptions, index);
        });

        mainView.getFoodReplacementPanel().addBackListener(e -> {
            mainView.getFoodReplacementPanel().clearFields();
            mainView.showCard(MainView.HOME);
        });
        mainView.getFoodReplacementPanel().addGetAlternativesButtonListener(e -> {
            // get selected meal and food item
            FoodReplacementPanel foodRep = mainView.getFoodReplacementPanel();
            Date date = foodRep.getSelectedDate();
            String type = foodRep.getSelectedMealType();

            List<Meal> meals = db.getMealsByDate(currentUser.getEmail(), date);

            Meal selectedMeal = null;
            for (Meal m: meals) {
                if (m.getDate().equals(date) && m.getType().equals(type)) {
                    selectedMeal = m;
                    break;
                }
            }

            if (selectedMeal == null)
                mainView.showError("Pick a meal first.");

            String foodItemString = foodRep.getSelectedFoodItem();
            FoodItem selectedFoodItem = null;
            for (FoodItem fi : selectedMeal.getFoodItems()) {
                if (fi.toString().equals(foodItemString)) {
                    selectedFoodItem = fi;
                    break;
                }
            }
            
            List<String[]> goalStrings = foodRep.getSelectedGoals();
            List<Goal> goals = new ArrayList<>();
            for (int i = 0; i < goalStrings.size(); i++) {
                String[] goalString = goalStrings.get(i);
                boolean increase = goalString[0].equals("increase");
                String nutrient = goalString[1];
                double intensity;
                double currentNutrientValue = db.getMealNutrtionalValue(selectedMeal).getNutrient(nutrient);
                if (goalString[3].equals("%")) {
                    intensity = currentNutrientValue * (1 + ( increase? 1 : -1 ) * Double.parseDouble(goalString[2])/100.0);
                } else {
                    intensity = currentNutrientValue + Double.parseDouble(goalString[2]);
                }

                goals.add(new Goal(nutrient, increase, intensity));
            }
            List<FoodItem> mealFoodItems = selectedMeal.getFoodItems();
            int foodItemIndex = -1;
            for (int i = 0; i < mealFoodItems.size(); i++) {
                if (mealFoodItems.get(i).equals(selectedFoodItem)) {
                    foodItemIndex = i;
                    break;
                }
            }
            List<FoodItem> validAlternatives = db.getSwappedFoodOptions(selectedMeal, foodItemIndex, goals);
            mainView.showMessage(validAlternatives.toString());
        });
    }

    private void handleLogin() {
        String email = mainView.getLoginPanel().getEmail();

        UserProfile user = db.getProfile(email);
        if (user != null) {
            currentUser = user;
            mainView.showMessage("Login successful!");
            mainView.getLoginPanel().clearFields(); // Clear fields on successful login
            mainView.getHomePanel().setWelcomeMessage(currentUser.getName());
            mainView.showCard(MainView.HOME);
        } else {
            mainView.showError("No user was found!");
            mainView.getLoginPanel().clearFields(); // Clear fields on failed login
        }
    }   

    private void handleSaveProfile() {
        EditProfilePanel panel = mainView.getEditProfilePanel();
        String name = panel.getName();
        String sex = panel.getSex();
        String unit = panel.getUnit();
        String height = panel.getHeightField();
        String weight = panel.getWeight();
        Date dob = panel.getDateOfBirth();
        String email = panel.getEmail();

        if (name.isEmpty() || sex.isEmpty() || unit.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || dob == null || email.isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        UserProfile updatedUser = new UserProfile(name, sex, email, unit, dob, Double.parseDouble(height), Double.parseDouble(weight));

        try {
            db.updateProfile(updatedUser);
            currentUser = updatedUser;
            mainView.showMessage("Profile updated successfully!");
            mainView.getHomePanel().setWelcomeMessage(currentUser.getName());
            mainView.showCard(MainView.HOME);
        } catch (Exception e) {
            mainView.showError("Failed to update profile.");
        }
    }

    private void handleRegister() {
        RegisterPanel panel = mainView.getRegisterPanel();
        String name = panel.getName();
        String sex = panel.getSex();
        String unit = panel.getUnit();
        String height = panel.getHeightField();
        String weight = panel.getWeight();
        Date dob = panel.getDateOfBirth();
        String email = panel.getEmail();

        if (name.isEmpty() || sex.isEmpty() || unit.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || dob == null ||
            email.isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        UserProfile user = new UserProfile(name, sex, email, unit, dob, Double.parseDouble(height), Double.parseDouble(weight));
        try {
            db.setProfile(user);
            mainView.showMessage("Registration successful!");
            mainView.showCard(MainView.LOGIN);
        } catch(Exception e) {
            mainView.showError("Registration failed. Please try again.");
        }
    }

    private <T> String[] makeComboboxOptions(List<T> list, Function<T, String> formatter) {
        String[] result = new String[list.size() + 1];
        result[0] = "< pick one >";
        for (int i = 0; i < list.size(); i++) {
            result[i + 1] = formatter.apply(list.get(i));
        }
        return result;
    }
}
