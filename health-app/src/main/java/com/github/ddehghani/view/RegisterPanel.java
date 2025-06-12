package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class RegisterPanel extends GradientPanel {
    private JButton registerButton;
    private JButton switchToLogin;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> unitComboBox;
    private JTextField heightField;
    private JTextField weightField;
    private JSpinner dobField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    
    public RegisterPanel() {
        JPanel card = new WhiteCardPanel();
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        sexComboBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        unitComboBox = new JComboBox<>(new String[] {"Metric", "Imperial"});
        heightField = new JTextField();
        weightField = new JTextField();
        dobField = new JSpinner(new SpinnerDateModel());
        dobField.setEditor(new JSpinner.DateEditor(dobField, "yyyy-MM-dd"));
        emailField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();

        registerButton = new GradientButton("Register");
        switchToLogin = new GradientButton("Back to Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        card.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        gbc.ipadx = 85;
        card.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 10;
        card.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        card.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        card.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        card.add(sexComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        card.add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        card.add(unitComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        card.add(new JLabel("Height:"), gbc);
        gbc.gridx = 1;
        card.add(heightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        card.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        card.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        card.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        card.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        card.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        card.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        card.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        card.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        card.add(new JLabel("Re-enter Pass:"), gbc);
        gbc.gridx = 1;
        card.add(confirmPasswordField, gbc);

        gbc.insets = new Insets(20, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 10;
        card.add(switchToLogin, gbc);
        gbc.gridx = 1;
        card.add(registerButton, gbc);

        setLayout(new GridBagLayout());
        add(card);
    }


    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getSex() {
        return (String) sexComboBox.getSelectedItem();
    }

    public String getUnit() {
        return (String) unitComboBox.getSelectedItem();
    }

    public String getHeightField() {
        return heightField.getText();
    }

    public String getWeight() {
        return weightField.getText();
    }

    public Date getDateOfBirth() {
        return (Date) dobField.getValue();
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

    public void addRegisterButtonListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addSwitchToLoginListener(ActionListener listener) {
        switchToLogin.addActionListener(listener);
    }

    public void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        sexComboBox.setSelectedIndex(0);
        unitComboBox.setSelectedIndex(0);
        heightField.setText("");
        weightField.setText("");
        dobField.setValue(new Date());
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}