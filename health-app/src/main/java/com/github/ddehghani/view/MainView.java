package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;

    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";

    public MainView() {
        setTitle("Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel(mainPanel, cardLayout);
        registerPanel = new RegisterPanel(mainPanel, cardLayout);

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(registerPanel, REGISTER);

        showCard(LOGIN); // Show login panel at startup
        add(mainPanel);
    }  

    /**
     * Method to show a specific card in the CardLayout.
     * 
     * @param cardName The name of the card to show.
     */
    public void showCard(String cardName) {
        cardLayout.show(mainPanel, cardName);
    }


    /**
     * Getters for the login and register panels.
     * 
     * @return The login or register panel.
     */
    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    /**
     * Getters for the login and register panels.
     * 
     * @return The login or register panel.
     */
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
