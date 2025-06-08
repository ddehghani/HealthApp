package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {

    private JButton loginButton;
    private JButton switchToRegister;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new GridLayout(4, 2, 5, 5));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        switchToRegister = new JButton("Register");

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(switchToRegister);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
    
    public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addSwitchToRegisterListener(ActionListener listener) {
        switchToRegister.addActionListener(listener);
    }

    public void clearFields() {
        emailField.setText("");
        passwordField.setText("");
    }
}