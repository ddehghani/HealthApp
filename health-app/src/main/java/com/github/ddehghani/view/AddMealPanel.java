package com.github.ddehghani.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class AddMealPanel extends GradientPanel {
    private JSpinner dateSpinner;
    private JComboBox<String> mealTypeComboBox;
    private java.util.List<JComboBox<String>> ingredientComboBoxes = new ArrayList<>();
    private java.util.List<JTextField> quantityFields = new ArrayList<>();
    private JPanel ingredientsPanel;
    private JButton addIngredientButton;
    private JButton backButton;
    private JButton addMealButton;

    public AddMealPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Date label and picker
        JLabel dateLabel = new JLabel("Meal Date:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(dateLabel, gbc);

        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
        dateSpinner.setEditor(dateEditor);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(dateSpinner, gbc);

        // Meal type label and combo box
        JLabel mealTypeLabel = new JLabel("Meal Type:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(mealTypeLabel, gbc);

        mealTypeComboBox = new JComboBox<>(new String[] {"Breakfast", "Lunch", "Dinner", "Snack"});
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(mealTypeComboBox, gbc);

        // Ingredients panel and add ingredient button
        ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new GridBagLayout());
        ingredientsPanel.setOpaque(false);
        GridBagConstraints ingGbc = new GridBagConstraints();
        ingGbc.insets = new Insets(2, 2, 2, 2);
        ingGbc.fill = GridBagConstraints.HORIZONTAL;
        addIngredientField(0);

        addIngredientButton = new JButton("Add Ingredient");
        addIngredientButton.addActionListener(e -> {
            if (ingredientComboBoxes.size() < 4) {
                addIngredientField(ingredientComboBoxes.size());
                ingredientsPanel.revalidate();
                ingredientsPanel.repaint();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(ingredientsPanel, gbc);

        gbc.gridy = 3;
        add(addIngredientButton, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setOpaque(false);
        backButton = new JButton("Back");
        addMealButton = new JButton("Add Meal");
        buttonsPanel.add(backButton);
        buttonsPanel.add(addMealButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        add(buttonsPanel, gbc);
    }

    private void addIngredientField(int index) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = index;
        gbc.anchor = GridBagConstraints.LINE_END;
        ingredientsPanel.add(new JLabel("Ingredient " + (index + 1) + ":"), gbc);

        JComboBox<String> ingredientComboBox = new JComboBox<>();
        ingredientComboBox.setPreferredSize(new Dimension(150, 25));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        ingredientsPanel.add(ingredientComboBox, gbc);
        ingredientComboBoxes.add(ingredientComboBox);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        ingredientsPanel.add(new JLabel("Qty:"), gbc);

        JTextField qtyField = new JTextField(6);
        gbc.gridx = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        ingredientsPanel.add(qtyField, gbc);
        quantityFields.add(qtyField);
    }

    public void setIngredientsList(String[] ingredients) {
        for (JComboBox<String> comboBox : ingredientComboBoxes) {
            comboBox.setModel(new DefaultComboBoxModel<>(ingredients));
        }
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addAddMealButtonListener(ActionListener listener) {
        addMealButton.addActionListener(listener);
    }

    public Date getSelectedDate() {
        return (Date) dateSpinner.getValue();
    }

    public String getSelectedMealType() {
        return (String) mealTypeComboBox.getSelectedItem();
    }

    public java.util.List<String> getSelectedIngredients() {
        return ingredientComboBoxes.stream()
                .map(cb -> (String) cb.getSelectedItem())
                .collect(Collectors.toList());
    }

    public java.util.List<String> getQuantities() {
        return quantityFields.stream()
                .map(JTextField::getText)
                .collect(Collectors.toList());
    }

    public void clearFields() {
        dateSpinner.setValue(new Date());
        mealTypeComboBox.setSelectedIndex(0);
        ingredientComboBoxes.forEach(cb -> cb.setSelectedIndex(0));
        // quantityFields.forEach(JTextField::setText);
        ingredientsPanel.removeAll();
        ingredientComboBoxes.clear();
        quantityFields.clear();
        addIngredientField(0);
        ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addAddMealListener(ActionListener listener) {
        addMealButton.addActionListener(listener);
    }

    public void setIngredients(String[] ingredients) {
        for (JComboBox<String> comboBox : ingredientComboBoxes) {
            comboBox.setModel(new DefaultComboBoxModel<>(ingredients));
        }
    }
}
