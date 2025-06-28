package com.github.ddehghani.view;

import javax.swing.*;

import java.awt.*;

public class MainView extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private HomePanel homePanel;
    private LogMealPanel logMealPanel;
    private EditProfilePanel editProfilePanel;
    private FoodReplacementPanel foodReplacementPanel;

    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";
    public static final String HOME = "Home";
    public static final String LOG_MEAL = "Log Meal";
    public static final String EDIT_PROFILE = "Edit Profile";
    public static final String FOOD_REPLACEMENT = "Food Replacement";
    

    public MainView() {
        setTitle("Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 600));
        setLocationRelativeTo(null);

        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(3000);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        homePanel = new HomePanel();
        logMealPanel = new LogMealPanel();
        editProfilePanel = new EditProfilePanel();
        foodReplacementPanel = new FoodReplacementPanel();

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(registerPanel, REGISTER);
        mainPanel.add(homePanel, HOME);
        mainPanel.add(logMealPanel, LOG_MEAL);
        mainPanel.add(editProfilePanel, EDIT_PROFILE);
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

    public LogMealPanel getLogMealPanel() {
        return logMealPanel;
    }

    public EditProfilePanel getEditProfilePanel() {
        return editProfilePanel;
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

    public boolean showConfirmDialog(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirm Action", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        return (result == JOptionPane.YES_OPTION);
    }
}
