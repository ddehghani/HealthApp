package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginPanel extends GradientPanel {
    private JButton loginButton;
    private JButton switchToRegister;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPanel() {
        emailField = new JTextField(15);
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        switchToRegister = new JButton("Register");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        add(loginButton, gbc);

        gbc.gridy++;
        add(new JLabel("Don't have an account?"), gbc);

        gbc.gridy++;
        add(switchToRegister, gbc);
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