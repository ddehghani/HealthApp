package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
public class HomePanel extends GradientPanel {
    private JLabel welcomeLabel;
    private JButton logMealButton;
    private JButton changeProfileButton;
    private JButton foodReplacementButton;
    private JButton logoutButton;
    private JButton nutrientTrendButton;
    private JButton averagePlateButton;

    public HomePanel() {
        setLayout(new GridBagLayout());
    
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        changeProfileButton = new GradientButton("Edit Profile");
        logMealButton = new GradientButton("Log Meal");
        foodReplacementButton = new GradientButton("Get Food Swaps");
        nutrientTrendButton = new GradientButton("Nutrient Intake Trend");
        averagePlateButton = new GradientButton("Average Plate");
        logoutButton = new GradientButton("Logout");

        JPanel cardPanel = new WhiteCardPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        gbc.gridy = 0;
        cardPanel.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        cardPanel.add(changeProfileButton, gbc);

        gbc.gridy = 2;
        cardPanel.add(logMealButton, gbc);

        gbc.gridy = 3;
        cardPanel.add(foodReplacementButton, gbc);

        gbc.gridy = 5;
        cardPanel.add(nutrientTrendButton, gbc);

        gbc.gridy = 6;
        cardPanel.add(averagePlateButton, gbc);

        gbc.gridy = 7;
        gbc.insets = new Insets(40, 10, 10, 10);
        cardPanel.add(logoutButton, gbc);

        add(cardPanel);
    }

    public void setWelcomeMessage(String firstName) {
        welcomeLabel.setText("Welcome, " + firstName + "!");
    }

    public void addLogMealListener(ActionListener listener) {
        logMealButton.addActionListener(listener);
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

    public void addNutrientTrendListener(ActionListener listener) {
        nutrientTrendButton.addActionListener(listener);
    }

    public void addAveragePlateListener(ActionListener listener) {
        averagePlateButton.addActionListener(listener);
    }
}