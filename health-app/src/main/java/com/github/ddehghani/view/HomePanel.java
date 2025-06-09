package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomePanel extends GradientPanel {
    private JLabel welcomeLabel;
    private JButton addMealButton,
                    changeProfileButton,
                    foodReplacementButton,
                    logoutButton;

    public HomePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        addMealButton = new JButton("Add Meal");
        gbc.gridy = 1;
        add(addMealButton, gbc);

        changeProfileButton = new JButton("Change Profile Settings");
        gbc.gridy = 2;
        add(changeProfileButton, gbc);

        foodReplacementButton = new JButton("Make Food Item Replacements");
        gbc.gridy = 3;
        add(foodReplacementButton, gbc);

        logoutButton = new JButton("Logout");
        gbc.gridy = 4;
        add(logoutButton, gbc);
    }

    public void setWelcomeMessage(String firstName) {
        welcomeLabel.setText("Welcome, " + firstName + "!");
    }

    public void addAddMealListener(ActionListener listener) {
        addMealButton.addActionListener(listener);
    }

    public void addChangeProfileListener(ActionListener listener) {
        changeProfileButton.addActionListener(listener);
    }

    public void addFoodReplacementListener(ActionListener listener) {
        foodReplacementButton.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}