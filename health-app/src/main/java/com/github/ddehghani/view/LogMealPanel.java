package com.github.ddehghani.view;

import javax.swing.*;

import com.github.ddehghani.model.Meal;
import com.github.ddehghani.model.Nutrition;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogMealPanel extends GradientPanel {
    private String[] ingredients = new String[]{};
    private JSpinner dateSpinner;
    private JComboBox<String> mealTypeComboBox;
    private List<JComboBox<String>> ingredientComboBoxes = new ArrayList<>();
    private List<JTextField> quantityFields = new ArrayList<>();
    private List<JComboBox<String>> unitComboBoxFields = new ArrayList<>();
    private JPanel ingredientsPanel;
    private JButton backButton;
    private JButton addMealButton;
    private JButton addIngredientButton;
    private JButton removeIngredientButton;
    private ActionListener ingredientSelectionListener;
    private MealJournalPanel mealJournalPanel;

    public LogMealPanel() {
        mealJournalPanel = new MealJournalPanel();
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        mealTypeComboBox = new JComboBox<>(new String[] {"Breakfast", "Lunch", "Dinner", "Snack"});

        ingredientsPanel = new JPanel();
        ingredientsPanel.setLayout(new BoxLayout(ingredientsPanel, BoxLayout.Y_AXIS));
        ingredientsPanel.setOpaque(false);

        backButton = new GradientButton("Back");
        addMealButton = new GradientButton("Add Meal");

        addIngredientButton = new GradientButton("+");
        addIngredientButton.addActionListener(e -> {
            if (ingredientComboBoxes.size() < 4) {
                addIngredientField(ingredientComboBoxes.size());
                applyIngredientSelectionListener();
                ingredientsPanel.revalidate();
                ingredientsPanel.repaint();
            }
        });

        removeIngredientButton = new GradientButton("-");
        removeIngredientButton.addActionListener(e -> {
            int lastIndex = ingredientComboBoxes.size() - 1;
            if (lastIndex >= 1) {
                ingredientComboBoxes.remove(lastIndex);
                quantityFields.remove(lastIndex);
                unitComboBoxFields.remove(lastIndex);
                ingredientsPanel.remove(ingredientsPanel.getComponentCount() - 1);
                ingredientsPanel.revalidate();
                ingredientsPanel.repaint();
            }
        });

        JPanel cardPanel = new WhiteCardPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        cardPanel.add(mealJournalPanel);

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(200, 3));
        separator.setForeground(new Color(120, 90, 195));  

        cardPanel.add(separator);
        
        JPanel firstRow = new JPanel();
        firstRow.setOpaque(false);
        firstRow.add(new JLabel("Meal Date:"));
        firstRow.add(dateSpinner);
        firstRow.add(Box.createHorizontalStrut(35));
        firstRow.add(new JLabel("Meal Type:"));
        firstRow.add(mealTypeComboBox);

        addIngredientField(0);

        JPanel ingredientsButtonsRow = new JPanel();
        ingredientsButtonsRow.setOpaque(false);
        ingredientsButtonsRow.add(addIngredientButton);
        ingredientsButtonsRow.add(removeIngredientButton);
        
        JPanel mealsButtonsRow = new JPanel();
        mealsButtonsRow.setOpaque(false);
        mealsButtonsRow.add(backButton);
        mealsButtonsRow.add(addMealButton);

        cardPanel.add(firstRow);
        cardPanel.add(ingredientsPanel);
        cardPanel.add(ingredientsButtonsRow);
        cardPanel.add(mealsButtonsRow);

        setLayout(new GridBagLayout());
        add(cardPanel);
    }

    private void addIngredientField(int index) {
        JPanel ingredientFieldPanel = new JPanel();
        ingredientFieldPanel.setOpaque(false);
        ingredientFieldPanel.add(new JLabel("Ingredient " + (index + 1) + ":"));

        JComboBox<String> ingredientComboBox = new JComboBox<>(ingredients);
        ingredientComboBox.setPreferredSize(new Dimension(160, 25));
        ingredientFieldPanel.add(ingredientComboBox);
        ingredientComboBoxes.add(ingredientComboBox);

        ingredientFieldPanel.add(new JLabel("Qty:"));

        JTextField qtyField = new JTextField(6);
        ingredientFieldPanel.add(qtyField);
        quantityFields.add(qtyField);

        JComboBox<String> unitBox = new JComboBox<>();
        ingredientFieldPanel.add(new JLabel("Unit:"));

        ingredientFieldPanel.add(unitBox);
        unitComboBoxFields.add(unitBox);

        ingredientsPanel.add(ingredientFieldPanel);
    }
    
    private void applyIngredientSelectionListener() {
        for (int i = 0; i < ingredientComboBoxes.size(); i++) {
            JComboBox<String> comboBox = ingredientComboBoxes.get(i);
            comboBox.setActionCommand("" + i);
            comboBox.addActionListener(ingredientSelectionListener);
        }
    }

    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addAddMealButtonListener(ActionListener listener) {
        addMealButton.addActionListener(listener);
    }

    public void addIngredientSelectionListener(ActionListener listener) {
        ingredientSelectionListener = listener;
        applyIngredientSelectionListener();
    }

    public Date getSelectedDate() {
        return (Date) dateSpinner.getValue();
    }

    public String getSelectedMealType() {
        return (String) mealTypeComboBox.getSelectedItem();
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
        for (JComboBox<String> comboBox : ingredientComboBoxes) {
            comboBox.setModel(new DefaultComboBoxModel<>(ingredients));
        }
    }

    public void setUnits(String[] units, int index) {
        unitComboBoxFields.get(index).setModel(new DefaultComboBoxModel<>(units));
    }


    public List<String[]> getSelectedIngredients() {
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < ingredientComboBoxes.size(); i++) {
            String name = (String) ingredientComboBoxes.get(i).getSelectedItem();
            String quantity = quantityFields.get(i).getText();
            String unit = (String) unitComboBoxFields.get(i).getSelectedItem();
            result.add( new String[]{ name, quantity, unit});
        }
        return result;
    }

    public void setInitialMealsAndNutritions(List<Meal> meals, List<Nutrition> nutritions) {
        mealJournalPanel.setInitialMealsAndNutritions(meals, nutritions);
    }

    public void clearFields() {
        clearInputFields();
        mealJournalPanel.clearFields();
    }

    public void clearInputFields() {
        dateSpinner.setValue(new Date());
        mealTypeComboBox.setSelectedIndex(0);
        ingredientComboBoxes.forEach(cb -> cb.setSelectedIndex(0));
        ingredientsPanel.removeAll();
        ingredientComboBoxes.clear();
        quantityFields.clear();
        unitComboBoxFields.clear();
        addIngredientField(0);
        applyIngredientSelectionListener();
        ingredientsPanel.revalidate();
        ingredientsPanel.repaint();
    }
}