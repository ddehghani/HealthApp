package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.SpinnerDateModel;

public class RegisterPanel extends JPanel {
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
        setLayout(new GridBagLayout());
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

        registerButton = new JButton("Register");
        switchToLogin = new JButton("Back to Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        add(sexComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        add(unitComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Height:"), gbc);
        gbc.gridx = 1;
        add(heightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("Re-enter Password:"), gbc);
        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(registerButton, gbc);
        gbc.gridx = 1;
        add(switchToLogin, gbc);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Color color1 = new Color(240, 240, 255);
        Color color2 = new Color(200, 200, 240);
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}