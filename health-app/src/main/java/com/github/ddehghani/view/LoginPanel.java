package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.formdev.flatlaf.extras.components.FlatTextField;


public class LoginPanel extends GradientPanel {
    private JButton loginButton;
    private JButton switchToRegister;
    private FlatTextField emailField;

    public LoginPanel() {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/logo.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel titleLabel = new JLabel(new ImageIcon(scaledImage));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        emailField = new FlatTextField();
        

        loginButton = new GradientButton("Login");
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);

        switchToRegister = new GradientButton("Register");
        switchToRegister.setContentAreaFilled(false);
        switchToRegister.setBorderPainted(false);

        JPanel card = new WhiteCardPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        card.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        
        card.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.ipadx = 100;
        card.add(emailField, gbc);

       
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        card.add(loginButton, gbc);

        gbc.gridy++;
        card.add(new JLabel("Don't have an account?"), gbc);

        gbc.gridy++;
        card.add(switchToRegister, gbc);

        setLayout(new GridBagLayout());
        add(card);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addSwitchToRegisterListener(ActionListener listener) {
        switchToRegister.addActionListener(listener);
    }

    public void clearFields() {
        emailField.setText("");
    }
}