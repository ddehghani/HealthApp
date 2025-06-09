package com.github.ddehghani.controller;

import com.github.ddehghani.model.*;
import com.github.ddehghani.view.*;
import java.util.Date;
import java.util.Optional;

public class MainController {
    private final MainView mainView;
    private final Database db;
    private User currentUser;

    public MainController() {
        mainView = new MainView();
        mainView.showCard(MainView.LOGIN); // Start with the login panel
        db = SqlDatabase.getInstance();
        registerEventHandlers();
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
        mainView.getHomePanel().addAddMealListener(e -> mainView.showCard(MainView.ADD_MEAL));
        mainView.getHomePanel().addChangeProfileListener(e -> {
            mainView.getChangeProfilePanel().setUserDetails(currentUser);
            mainView.showCard(MainView.CHANGE_PROFILE);
        });
        mainView.getHomePanel().addFoodReplacementListener(e -> mainView.showCard(MainView.FOOD_REPLACEMENT));
        mainView.getHomePanel().addLogoutListener(e -> {
            currentUser = null;
            mainView.showMessage("You have been logged out.");
            mainView.showCard(MainView.LOGIN);
        });
        mainView.getAddMealPanel().addBackListener(e -> mainView.showCard(MainView.HOME));
        mainView.getAddMealPanel().addAddMealListener(e -> {
            // db.addMeal(new Meal(
                
            // ), currentUser.getEmail());
            mainView.showMessage("Add Meal feature is not implemented yet.");
        });
        mainView.getChangeProfilePanel().addSaveProfileListener(e -> handleSaveProfile());
        mainView.getChangeProfilePanel().addBackListener(e -> mainView.showCard(MainView.HOME));

        // mainView.getFoodReplacementPanel().addBackListener(e -> mainView.showCard(MainView.HOME));
        // mainView.getFoodReplacementPanel().addReplaceListener(e -> {
        //     // Handle food replacement logic here
        //     mainView.showMessage("Food replacement feature is not implemented yet.");
        // });
    }

    private void handleLogin() {
        String email = mainView.getLoginPanel().getEmail();
        String password = mainView.getLoginPanel().getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            mainView.showError("Email and password are required.");
            return;
        }

        Optional<User> userOpt = db.authenticateUser(email, password);
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            mainView.showMessage("Login successful!");
            mainView.getLoginPanel().clearFields(); // Clear fields on successful login
            mainView.showCard(MainView.HOME);
        } else {
            mainView.showError("Invalid email or password.");
            mainView.getLoginPanel().clearFields(); // Clear fields on failed login
        }
    }

    private void handleSaveProfile() {
        ChangeProfilePanel panel = mainView.getChangeProfilePanel();
        String firstName = panel.getFirstName();
        String lastName = panel.getLastName();
        String sex = panel.getSex();
        String unit = panel.getUnit();
        String height = panel.getHeightField();
        String weight = panel.getWeight();
        Date dob = panel.getDateOfBirth();
        String email = panel.getEmail();
        String newPassword = panel.getNewPassword();


        if (firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || unit.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || dob == null || email.isEmpty() ||
            newPassword.isEmpty() || panel.getReenteredPassword().isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        if (!newPassword.equals(panel.getReenteredPassword())) {
            mainView.showError("Passwords do not match.");
            return;
        }

        User updatedUser = new User(firstName, lastName, sex, unit, height, weight, dob, email, newPassword);

        if (db.updateUserProfile(updatedUser)) {
            currentUser = updatedUser;
            mainView.showMessage("Profile updated successfully!");
            mainView.showCard(MainView.HOME);
        } else {
            mainView.showError("Failed to update profile.");
        }
    }

    private void handleRegister() {
        RegisterPanel panel = mainView.getRegisterPanel();
        String firstName = panel.getFirstName();
        String lastName = panel.getLastName();
        String sex = panel.getSex();
        String unit = panel.getUnit();
        String height = panel.getHeightField();
        String weight = panel.getWeight();
        Date dob = panel.getDateOfBirth();
        String email = panel.getEmail();
        String password = panel.getPassword();
        String confirmPassword = panel.getConfirmPassword();

        if (firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || unit.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || dob == null ||
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mainView.showError("Passwords do not match.");
            return;
        }

        User user = new User(firstName, lastName, sex, unit, height, weight, dob, email, password);
        if (db.registerUser(user)) {
            mainView.showMessage("Registration successful!");
            mainView.showCard(MainView.LOGIN);
        } else {
            mainView.showError("Registration failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        new MainController();
    } 
}
