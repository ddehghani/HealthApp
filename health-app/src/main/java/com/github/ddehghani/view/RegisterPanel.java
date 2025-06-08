package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    public JButton registerButton;
    public JButton switchToLogin;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    
    public RegisterPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new GridLayout(6, 2, 5, 5));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        registerButton = new JButton("Register");
        switchToLogin = new JButton("Back to Login");

        add(new JLabel("First Name:"));
        add(firstNameField);
        add(new JLabel("Last Name:"));
        add(lastNameField);
        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Re-enter Password:"));
        add(confirmPasswordField);
        add(registerButton);
        add(switchToLogin);

        switchToLogin.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}