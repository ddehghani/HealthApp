package com.github.ddehghani.controller;

import com.github.ddehghani.model.*;
import com.github.ddehghani.view.*;

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
        mainView.getRegisterPanel().registerButton.addActionListener(e -> handleRegister());
        mainView.getRegisterPanel().switchToLogin.addActionListener(e -> switchToLoginPanel());
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
            // Proceed to the next part of the application
        } else {
            mainView.showError("Invalid email or password.");
            mainView.getLoginPanel().clearFields(); // Clear fields on failed login
        }
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
        String email = mainView.getRegisterPanel().getEmail();
        String password = mainView.getRegisterPanel().getPassword();
        String confirmPassword = mainView.getRegisterPanel().getConfirmPassword();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            mainView.showError("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            mainView.showError("Passwords do not match.");
            return;
        }

        if (db.registerUser(firstName, lastName, email, password)) {
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
