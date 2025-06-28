package com.github.ddehghani.view;

import javax.swing.*;

import com.github.ddehghani.model.UserProfile;

import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class EditProfilePanel extends GradientPanel {
    private JButton saveProfileButton;
    private JButton backButton;
    private JButton deleteButton;
    private JTextField nameField;
    private JComboBox<String> sexComboBox;
    private JComboBox<String> unitComboBox;
    private JTextField heightField;
    private JTextField weightField;
    private JSpinner dobField;
    private JLabel emailField;
    
    public EditProfilePanel() {
        emailField = new JLabel();
        emailField.setOpaque(true);
        emailField.setBackground(Color.LIGHT_GRAY);
        emailField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        nameField = new JTextField();
        sexComboBox = new JComboBox<>(new String[] {"Male", "Female", "Other"});
        unitComboBox = new JComboBox<>(new String[] {"Metric", "Imperial"});
        heightField = new JTextField();
        weightField = new JTextField();
        dobField = new JSpinner(new SpinnerDateModel());
        dobField.setEditor(new JSpinner.DateEditor(dobField, "yyyy-MM-dd"));

        saveProfileButton = new GradientButton("Save Profile");
        backButton = new GradientButton("Back");
        deleteButton = new GradientButton("Delete Profile");

        JPanel cardPanel = new WhiteCardPanel();
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        cardPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        cardPanel.add(new JLabel("Sex:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(sexComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        cardPanel.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        cardPanel.add(new JLabel("Height:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(heightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        cardPanel.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(weightField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        cardPanel.add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        cardPanel.add(unitComboBox, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 7;
        cardPanel.add(deleteButton, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 8;
        cardPanel.add(saveProfileButton, gbc);
        gbc.gridx = 1;
        cardPanel.add(backButton, gbc);

        setLayout(new GridBagLayout());
        add(cardPanel);
    }


    public String getName() {
        return nameField.getText();
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

    public void addSaveProfileListener(ActionListener listener) {
        saveProfileButton.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

     public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void clearFields() {
        nameField.setText("");
        sexComboBox.setSelectedIndex(0);
        unitComboBox.setSelectedIndex(0);
        heightField.setText("");
        weightField.setText("");
        dobField.setValue(new Date());
        emailField.setText("");
    }


    public void setUserDetails(UserProfile currentUser) {
        nameField.setText(currentUser.getName());
        sexComboBox.setSelectedItem(currentUser.getSex());
        unitComboBox.setSelectedItem(currentUser.getUnitOfMeasurement());
        heightField.setText(String.valueOf(currentUser.getHeight()));
        weightField.setText(String.valueOf(currentUser.getWeight()));
        dobField.setValue(currentUser.getDob());
        emailField.setText(currentUser.getEmail());
    }
}