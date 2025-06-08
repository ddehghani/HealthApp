package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private HomePanel homePanel;

    public static final String LOGIN = "Login";
    public static final String REGISTER = "Register";
    public static final String HOME = "Home";

    public MainView() {
        setTitle("Health App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null); // Center the window

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        loginPanel = new LoginPanel();
        registerPanel = new RegisterPanel();
        homePanel = new HomePanel();

        mainPanel.add(loginPanel, LOGIN);
        mainPanel.add(registerPanel, REGISTER);
        mainPanel.add(homePanel, HOME);

        showCard(LOGIN); // Show login panel at startup
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
