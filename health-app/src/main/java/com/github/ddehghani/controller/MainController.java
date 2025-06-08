package com.github.ddehghani.controller;

import com.github.ddehghani.model.*;
import com.github.ddehghani.view.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainController {
    private final MainView mainView;
    private final Database db;

    public MainController() {
        mainView = new MainView();
        db = SqlDatabase.getInstance();
        registerEventHandlers();
        mainView.setVisible(true);
    }

    private void registerEventHandlers() {
        mainView.getLoginPanel().addLoginButtonListener(e -> handleLogin());
        mainView.getLoginPanel().addSwitchToRegisterListener(e -> switchToRegisterPanel());
        mainView.getRegisterPanel().addSwitchToLoginListener(e -> switchToLoginPanel());
        mainView.getRegisterPanel().addRegisterButtonListener(e -> handleRegister());
    }

    private void handleLogin() {
        String email = mainView.getLoginPanel().getEmail();
        String password = mainView.getLoginPanel().getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            mainView.showError("Email and password are required.");
            return;
        }

        if (db.authenticateUser(email, password)) {
            mainView.showMessage("Login successful!");
            switchToHomePanel();
        } else {
            mainView.showError("Invalid email or password.");
            mainView.getLoginPanel().clearFields(); // Clear fields on failed login
        }
    }

    private void switchToHomePanel() {
        mainView.showCard(MainView.HOME);
    }

    private void switchToRegisterPanel() {
        mainView.showCard(MainView.REGISTER);
    }

    private void switchToLoginPanel() {
        mainView.showCard(MainView.LOGIN);
    }

    private void handleRegister() {
        String firstName = mainView.getRegisterPanel().getFirstName();
        String lastName = mainView.getRegisterPanel().getLastName();
        String sex = mainView.getRegisterPanel().getSex();
        String unit = mainView.getRegisterPanel().getUnit();
        String height = mainView.getRegisterPanel().getHeightField();
        String weight = mainView.getRegisterPanel().getWeight();
        Date dobDate = mainView.getRegisterPanel().getDateOfBirth();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dob = sdf.format(dobDate);
        String email = mainView.getRegisterPanel().getEmail();
        String password = mainView.getRegisterPanel().getPassword();
        String confirmPassword = mainView.getRegisterPanel().getConfirmPassword();

        if (firstName.isEmpty() || lastName.isEmpty() || sex.isEmpty() || unit.isEmpty() ||
            height.isEmpty() || weight.isEmpty() || dob.isEmpty() ||
            email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mainView.showError("Passwords do not match.");
            return;
        }

        if (db.registerUser(firstName, lastName, sex, unit, height, weight, dob, email, password)) {
            mainView.showMessage("Registration successful!");
            switchToLoginPanel();
        } else {
            mainView.showError("Registration failed. Please try again.");
        }
    }

    public static void main(String[] args) {
        new MainController();
    } 
}
