package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private HomePanel homePanel;
    private AddMealPanel addMealPanel;
    private ChangeProfilePanel changeProfilePanel;
    private FoodReplacementPanel foodReplacementPanel;

    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";
    public static final String HOME = "Home";
    public static final String ADD_MEAL = "Add Meal";
    public static final String CHANGE_PROFILE = "Change Profile";
    public static final String FOOD_REPLACEMENT = "Food Replacement";
    

    public MainView() {
        setTitle("Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        homePanel = new HomePanel();
        addMealPanel = new AddMealPanel();
        changeProfilePanel = new ChangeProfilePanel();
        foodReplacementPanel = new FoodReplacementPanel();

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(registerPanel, REGISTER);
        mainPanel.add(homePanel, HOME);
        mainPanel.add(addMealPanel, ADD_MEAL);
        mainPanel.add(changeProfilePanel, CHANGE_PROFILE);
        mainPanel.add(foodReplacementPanel, FOOD_REPLACEMENT);
        
        add(mainPanel);
    }  

    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public AddMealPanel getAddMealPanel() {
        return addMealPanel;
    }

    public ChangeProfilePanel getChangeProfilePanel() {
        return changeProfilePanel;
    }

    public FoodReplacementPanel getFoodReplacementPanel() {
        return foodReplacementPanel;
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
