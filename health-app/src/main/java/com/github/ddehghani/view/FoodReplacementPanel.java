package com.github.ddehghani.view;

import javax.swing.*;
import com.github.ddehghani.controller.Goal;
import java.awt.*;
import java.awt.event.ActionListener;

public class FoodReplacementPanel extends GradientPanel {
    private final JComboBox<String> mealComboBox;

    private final JComboBox<String> goalTypeComboBox1;
    private final JComboBox<String> increaseDecreaseComboBox1;

    private final JComboBox<String> intensityComboBox1;
    private final JTextField intensityValueField1;
    private final JComboBox<String> intensityUnitComboBox1;

    private final JComboBox<String> goalTypeComboBox2;
    private final JComboBox<String> increaseDecreaseComboBox2;

    private final JComboBox<String> intensityComboBox2;
    private final JTextField intensityValueField2;
    private final JComboBox<String> intensityUnitComboBox2;

    private final JButton addGoalButton;
    private final JButton removeGoalButton;

    private final JPanel secondGoalPanel;

    private final JButton backButton;
    private final JButton replaceFoodItemButton;

    public FoodReplacementPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2,2,2,2);
        gbc.anchor = GridBagConstraints.WEST;

        // Meal combo box
        JLabel mealLabel = new JLabel("Meal:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(mealLabel, gbc);

        mealComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(mealComboBox, gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        // First goal line 1: "I want to" + increase/decrease + goal type
        JLabel iWantToLabel1 = new JLabel("I want to");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(iWantToLabel1, gbc);

        increaseDecreaseComboBox1 = new JComboBox<>(new String[]{"increase", "decrease"});
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(increaseDecreaseComboBox1, gbc);

        goalTypeComboBox1 = new JComboBox<>(Goal.GOAL_TYPES.toArray(new String[0]));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(goalTypeComboBox1, gbc);
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        // First goal line 2: Intensity label + combo box + "or" + numeric field + unit combo box
        JLabel intensityLabel1 = new JLabel("By");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(intensityLabel1, gbc);

        intensityComboBox1 = new JComboBox<>(Goal.INTENSITY_LEVELS.keySet().toArray(new String[0]));
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(intensityComboBox1, gbc);

        JLabel orLabel1 = new JLabel("or");
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(orLabel1, gbc);

        intensityValueField1 = new JTextField(3);
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(intensityValueField1, gbc);

        intensityUnitComboBox1 = new JComboBox<>(new String[]{"%", "calories", "g"});
        gbc.gridx = 4;
        gbc.gridy = 2;
        add(intensityUnitComboBox1, gbc);

        // Buttons to add/remove second goal (moved below second goal panel)
        addGoalButton = new JButton("Add Goal");
        removeGoalButton = new JButton("Remove Goal");
        removeGoalButton.setVisible(false);

        // Second goal panel (initially hidden)
        secondGoalPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(2,2,2,2);
        gbc2.anchor = GridBagConstraints.WEST;

        JLabel iWantToLabel2 = new JLabel("I want to");
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        secondGoalPanel.add(iWantToLabel2, gbc2);

        increaseDecreaseComboBox2 = new JComboBox<>(new String[]{"increase", "decrease"});
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        secondGoalPanel.add(increaseDecreaseComboBox2, gbc2);

        goalTypeComboBox2 = new JComboBox<>(Goal.GOAL_TYPES.toArray(new String[0]));
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.gridwidth = 2;
        gbc2.fill = GridBagConstraints.HORIZONTAL;
        secondGoalPanel.add(goalTypeComboBox2, gbc2);
        gbc2.gridwidth = 1;
        gbc2.fill = GridBagConstraints.NONE;

        JLabel intensityLabel2 = new JLabel("Intensity");
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        secondGoalPanel.add(intensityLabel2, gbc2);

        intensityComboBox2 = new JComboBox<>(Goal.INTENSITY_LEVELS.keySet().toArray(new String[0]));
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        secondGoalPanel.add(intensityComboBox2, gbc2);

        JLabel orLabel2 = new JLabel("or");
        gbc2.gridx = 2;
        gbc2.gridy = 1;
        secondGoalPanel.add(orLabel2, gbc2);

        intensityValueField2 = new JTextField(3);
        gbc2.gridx = 3;
        gbc2.gridy = 1;
        secondGoalPanel.add(intensityValueField2, gbc2);

        intensityUnitComboBox2 = new JComboBox<>(new String[]{"%", "calories", "g"});
        gbc2.gridx = 4;
        gbc2.gridy = 1;
        secondGoalPanel.add(intensityUnitComboBox2, gbc2);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        secondGoalPanel.setVisible(false);
        secondGoalPanel.setOpaque(false); // Make the second goal panel transparent
        add(secondGoalPanel, gbc);

        // Add the add/remove goal buttons panel after the second goal panel
        JPanel goalButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goalButtonsPanel.setOpaque(false);
        goalButtonsPanel.add(addGoalButton);
        goalButtonsPanel.add(removeGoalButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(goalButtonsPanel, gbc);
        gbc.gridwidth = 1;

        // Button actions
        addGoalButton.addActionListener(e -> {
            secondGoalPanel.setVisible(true);
            addGoalButton.setVisible(false);
            removeGoalButton.setVisible(true);
            revalidate();
            repaint();
        });

        removeGoalButton.addActionListener(e -> {
            secondGoalPanel.setVisible(false);
            addGoalButton.setVisible(true);
            removeGoalButton.setVisible(false);
            revalidate();
            repaint();
        });

        // Buttons panel at the bottom
        backButton = new JButton("Back");
        replaceFoodItemButton = new JButton("Replace Food Item");

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionButtonsPanel.setOpaque(false);
        actionButtonsPanel.add(backButton);
        actionButtonsPanel.add(replaceFoodItemButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        add(actionButtonsPanel, gbc);
    }

    public void setGoalOptions(String[] goals) {
        DefaultComboBoxModel<String> model1 = new DefaultComboBoxModel<>(goals);
        goalTypeComboBox1.setModel(model1);
        DefaultComboBoxModel<String> model2 = new DefaultComboBoxModel<>(goals);
        goalTypeComboBox2.setModel(model2);
    }

    // Getters for selected values

    public String getSelectedMeal() {
        return (String) mealComboBox.getSelectedItem();
    }

    public String getIncreaseDecrease1() {
        return (String) increaseDecreaseComboBox1.getSelectedItem();
    }

    public String getGoalType1() {
        return (String) goalTypeComboBox1.getSelectedItem();
    }

    public String getIntensitySelection1() {
        return (String) intensityComboBox1.getSelectedItem();
    }

    public String getIntensityValue1() {
        return intensityValueField1.getText();
    }

    public String getIntensityUnit1() {
        return (String) intensityUnitComboBox1.getSelectedItem();
    }

    public boolean isSecondGoalVisible() {
        return secondGoalPanel.isVisible();
    }

    public String getIncreaseDecrease2() {
        if (!isSecondGoalVisible()) return null;
        return (String) increaseDecreaseComboBox2.getSelectedItem();
    }

    public String getGoalType2() {
        if (!isSecondGoalVisible()) return null;
        return (String) goalTypeComboBox2.getSelectedItem();
    }

    public String getIntensitySelection2() {
        if (!isSecondGoalVisible()) return null;
        return (String) intensityComboBox2.getSelectedItem();
    }

    public String getIntensityValue2() {
        if (!isSecondGoalVisible()) return null;
        return intensityValueField2.getText();
    }

    public String getIntensityUnit2() {
        if (!isSecondGoalVisible()) return null;
        return (String) intensityUnitComboBox2.getSelectedItem();
    }

    // Setters for action listeners

    public void setAddGoalButtonListener(ActionListener listener) {
        addGoalButton.addActionListener(listener);
    }

    public void setRemoveGoalButtonListener(ActionListener listener) {
        removeGoalButton.addActionListener(listener);
    }

    // Removed setDateChangeListener since there is no date picker anymore

    public void setIncreaseDecrease1Listener(ActionListener listener) {
        increaseDecreaseComboBox1.addActionListener(listener);
    }

    public void setGoalType1Listener(ActionListener listener) {
        goalTypeComboBox1.addActionListener(listener);
    }

    public void setIntensitySelection1Listener(ActionListener listener) {
        intensityComboBox1.addActionListener(listener);
    }

    public void setIntensityUnit1Listener(ActionListener listener) {
        intensityUnitComboBox1.addActionListener(listener);
    }

    public void setIncreaseDecrease2Listener(ActionListener listener) {
        increaseDecreaseComboBox2.addActionListener(listener);
    }

    public void setGoalType2Listener(ActionListener listener) {
        goalTypeComboBox2.addActionListener(listener);
    }

    public void setIntensitySelection2Listener(ActionListener listener) {
        intensityComboBox2.addActionListener(listener);
    }

    public void setIntensityUnit2Listener(ActionListener listener) {
        intensityUnitComboBox2.addActionListener(listener);
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addReplaceListener(ActionListener listener) {
        replaceFoodItemButton.addActionListener(listener);
    }

    public void setMealOptions(String[] meals) {
        mealComboBox.setModel(new DefaultComboBoxModel<>(meals));
    }
}