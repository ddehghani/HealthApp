package com.github.ddehghani.view;

import javax.swing.*;

import com.github.ddehghani.model.User;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class ChangeProfilePanel extends GradientPanel {
    private JButton saveProfileButton;
    private JButton backButton;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> unitComboBox;
    private JTextField heightField;
    private JTextField weightField;
    private JSpinner dobField;
    private JTextField emailField;
    private JPasswordField newPasswordField;
    private JPasswordField reenterPasswordField;
    
    public ChangeProfilePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        emailField = new JTextField();
        emailField.setEditable(false);

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        sexComboBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        unitComboBox = new JComboBox<>(new String[] {"Metric", "Imperial"});
        heightField = new JTextField();
        weightField = new JTextField();
        dobField = new JSpinner(new SpinnerDateModel());
        dobField.setEditor(new JSpinner.DateEditor(dobField, "yyyy-MM-dd"));

        saveProfileButton = new JButton("Save Profile");
        backButton = new JButton("Back");

        newPasswordField = new JPasswordField();
        reenterPasswordField = new JPasswordField();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        add(sexComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        add(unitComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Height:"), gbc);
        gbc.gridx = 1;
        add(heightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        add(new JLabel("New Password:"), gbc);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        add(new JLabel("Re-enter Password:"), gbc);
        gbc.gridx = 1;
        add(reenterPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        add(saveProfileButton, gbc);
        gbc.gridx = 1;
        add(backButton, gbc);
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

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public String getReenteredPassword() {
        return new String(reenterPasswordField.getPassword());
    }

    public void addSaveProfileListener(ActionListener listener) {
        saveProfileButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
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
        newPasswordField.setText("");
        reenterPasswordField.setText("");
    }


    public void setUserDetails(User currentUser) {
        firstNameField.setText(currentUser.getFirstName());
        lastNameField.setText(currentUser.getLastName());
        sexComboBox.setSelectedItem(currentUser.getSex());
        unitComboBox.setSelectedItem(currentUser.getUnit());
        heightField.setText(currentUser.getHeight());
        weightField.setText(currentUser.getWeight());
        dobField.setValue(currentUser.getDob());
        emailField.setText(currentUser.getEmail());
        newPasswordField.setText(""); // Clear password fields
        reenterPasswordField.setText("");
    }
}