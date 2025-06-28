package com.github.ddehghani.view;

import com.github.ddehghani.model.FoodItem;
import com.github.ddehghani.model.Meal;
import com.github.ddehghani.model.Nutrition;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FoodReplacementPanel extends GradientPanel {
    private JPanel goalPanel;
    private JComboBox<String> mealTypeComboBox;
    private JComboBox<String> foodItemComboBox;
    private JSpinner dateField;
    private SpinnerDateModel model;

    private JButton addGoalButton;
    private JButton removeGoalButton;
    private JButton backButton;
    private JButton getAlternativesButton;

    private List<JComboBox<String>> increaseDecreaseComboBoxes = new ArrayList<>();
    private List<JComboBox<String>> nutrientComboBoxes = new ArrayList<>();
    private List<JComboBox<String>> intensityComboBoxes = new ArrayList<>();
    private List<JTextField> intensityValueFields = new ArrayList<>();
    private List<JComboBox<String>> intensityUnitComboBoxes = new ArrayList<>();

    public static final Map<String, Double> intensityLevelsMap = Map.of(
        "a little bit", 10.0,
        "a moderate amount", 20.0,
        "a significant amount", 30.0
    );

    private String[] nutrientsNames = new String[] {};

    private ActionListener nutrientComboBoxesListener;

    private MealJournalPanel mealJournalPanel;

    public FoodReplacementPanel() {
        mealJournalPanel = new MealJournalPanel();

        backButton = new GradientButton("Back");
        getAlternativesButton = new GradientButton("Get Alternative Food Items");
        addGoalButton = new GradientButton("Add Goal");
        removeGoalButton = new GradientButton("Remove Goal");
        removeGoalButton.setVisible(false);

        model = new SpinnerDateModel();
        dateField = new JSpinner(model);
        dateField.setEditor(new JSpinner.DateEditor(dateField, "yyyy-MM-dd"));

        mealTypeComboBox = new JComboBox<>();
        mealTypeComboBox.setPreferredSize(new Dimension(100,25));

        foodItemComboBox = new JComboBox<>();
        foodItemComboBox.setPreferredSize(new Dimension(120,25));

        addGoalButton.addActionListener(e -> {
            addGoalField(nutrientComboBoxes.size());
            applyNutrientComboBoxesListener();

            addGoalButton.setVisible(false);
            removeGoalButton.setVisible(true);

            goalPanel.revalidate();
            goalPanel.repaint();
        });


        removeGoalButton.addActionListener(e -> {
            int lastIndex = nutrientComboBoxes.size() - 1;

            increaseDecreaseComboBoxes.remove(lastIndex);
            nutrientComboBoxes.remove(lastIndex);
            intensityComboBoxes.remove(lastIndex);
            intensityUnitComboBoxes.remove(lastIndex);

            goalPanel.remove(goalPanel.getComponentCount() - 1);

            addGoalButton.setVisible(true);
            removeGoalButton.setVisible(false);

            goalPanel.revalidate();
            goalPanel.repaint();
        });


        JPanel cardPanel = new WhiteCardPanel();

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setPreferredSize(new Dimension(200, 3));
        separator.setForeground(new Color(120, 90, 195));  

        JPanel firstRow = new JPanel();
        firstRow.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
       
        firstRow.add(new JLabel("Date:"));
        firstRow.add(dateField);
        firstRow.add(new JLabel("Meal:"));
        firstRow.add(mealTypeComboBox);
        firstRow.add(new JLabel("Food Item:"));
        firstRow.add(foodItemComboBox);

        goalPanel = new JPanel();
        goalPanel.setLayout(new BoxLayout(goalPanel, BoxLayout.Y_AXIS));
        goalPanel.setOpaque(false);
        goalPanel.add(new JLabel("Goals: "));

        addGoalField(0);

        buttonPanel.add(addGoalButton);
        buttonPanel.add(removeGoalButton);

        JPanel actionButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionButtonsPanel.setOpaque(false);
        actionButtonsPanel.add(backButton);
        actionButtonsPanel.add(getAlternativesButton);

        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.add(mealJournalPanel);
        cardPanel.add(separator);
        cardPanel.add(firstRow);
        cardPanel.add(goalPanel);
        cardPanel.add(buttonPanel);
        cardPanel.add(actionButtonsPanel);

        setLayout(new GridBagLayout());
        add(cardPanel);
    }

    private void addGoalField(int index) {
        JPanel goalFieldPanel = new JPanel();
        goalFieldPanel.setOpaque(false);
       
        JPanel firstRow = new JPanel();
        firstRow.setOpaque(false);
        JPanel secondRow = new JPanel();
        secondRow.setOpaque(false);

        goalFieldPanel.setOpaque(false);
        firstRow.setOpaque(false);
        secondRow.setOpaque(false);

        firstRow.add(new JLabel("I want to "));

        JComboBox<String> increaseDecreaseComboBox = new JComboBox<>(new String[]{"increase", "decrease"});
        increaseDecreaseComboBoxes.add(increaseDecreaseComboBox);
        firstRow.add(increaseDecreaseComboBox);
        
        JComboBox<String> nutrientComboBox = new JComboBox<>(nutrientsNames);
        nutrientComboBox.setPreferredSize(new Dimension(300, 25));
        nutrientComboBoxes.add(nutrientComboBox);
        firstRow.add(nutrientComboBox);

        secondRow.add(new JLabel(" by "));

        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(intensityLevelsMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());

        String[] intensityLevelsOptions = new String[sortedEntries.size() + 1];
        intensityLevelsOptions[0] = "< not selected >";

        for (int i = 0; i < sortedEntries.size(); i++)
            intensityLevelsOptions[i + 1] = sortedEntries.get(i).getKey();

        JComboBox<String> intensityComboBox = new JComboBox<>(intensityLevelsOptions);
        intensityComboBoxes.add(intensityComboBox);

        secondRow.add(intensityComboBox);

        secondRow.add(new JLabel(" or "));

        JTextField intensityValueField = new JTextField(3);
        intensityValueFields.add(intensityValueField);
        secondRow.add(intensityValueField);

        JComboBox<String> intensityUnitComboBox = new JComboBox<>();
        intensityUnitComboBox.setModel(new DefaultComboBoxModel<>(new String[] {"%"}));
        intensityUnitComboBoxes.add(intensityUnitComboBox);
        secondRow.add(intensityUnitComboBox);

        intensityComboBox.addActionListener(e -> {
            intensityUnitComboBox.setSelectedIndex(0);
            intensityValueField.setText(intensityLevelsMap.get(((JComboBox<String>) e.getSource()).getSelectedItem()).toString());
        });


        goalFieldPanel.setLayout(new BoxLayout(goalFieldPanel, BoxLayout.Y_AXIS));
        goalFieldPanel.add(firstRow);
        goalFieldPanel.add(secondRow);

        goalPanel.add(goalFieldPanel);
    }
    
    private void applyNutrientComboBoxesListener() {
        for (int i = 0; i < nutrientComboBoxes.size(); i++) {
            JComboBox<String> comboBox = nutrientComboBoxes.get(i);
            comboBox.setActionCommand("" + i);
            comboBox.addActionListener(nutrientComboBoxesListener);
        }
    }
    
    public void setNutrientsNames(String[] nutrients) {
        nutrientsNames = nutrients;
        for (JComboBox<String> comboBox : nutrientComboBoxes)
            comboBox.setModel(new DefaultComboBoxModel<>(nutrients));
    }

    public String getSelectedMealType() {
        return (String) mealTypeComboBox.getSelectedItem();
    }

     public Date getSelectedDate() {
        return model.getDate();
    }

    public List<String[]> getSelectedGoals() {
        List<String[]> result = new ArrayList<>();
        for (int i = 0; i < nutrientComboBoxes.size(); i++) {
            String increaseDecrease = (String) increaseDecreaseComboBoxes.get(i).getSelectedItem();
            String nutrient = (String) nutrientComboBoxes.get(i).getSelectedItem();
            String intensityValue = intensityValueFields.get(i).getText();
            String intensityUnit = (String) intensityUnitComboBoxes.get(i).getSelectedItem();
            result.add(new String[] {increaseDecrease, nutrient, intensityValue, intensityUnit});
        }
        return result;
    }

    public List<String> getSelectedNutrients() {
        List<String> selectedNutrients = new ArrayList<>();
        for (JComboBox<String> comboBox : nutrientComboBoxes) {
            String selectedNutrient = (String) comboBox.getSelectedItem();
            if (!selectedNutrient.equals("< not selected >"))
                selectedNutrients.add(selectedNutrient);
        }
        return selectedNutrients;
    }

    public String getSelectedFoodItem() {
        return (String)foodItemComboBox.getSelectedItem();
        
    }

    public void setNutrientComboBoxesListener(ActionListener listener) {
        nutrientComboBoxesListener = listener;
        applyNutrientComboBoxesListener();
    }

    public void setIntensityUnits(String[] units, int index) {
        intensityUnitComboBoxes.get(index).setModel(new DefaultComboBoxModel<>(units));
    }

    public void setFoodItemNames(String[] foodItemNames) {
        foodItemComboBox.setModel(new DefaultComboBoxModel<>(foodItemNames));
    }

    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void addGetAlternativesButtonListener(ActionListener listener) {
        getAlternativesButton.addActionListener(listener);
    }

    public void addDateChangeListener(ChangeListener listener) {
        dateField.addChangeListener(listener);
    }

    public void setMealTypes(String[] meals) {
        mealTypeComboBox.setModel(new DefaultComboBoxModel<>(meals));
    }

    public void addMealTypeComboBoxListener(ActionListener listener) {
        mealTypeComboBox.addActionListener(listener);
    }

    
    public void setInitialMealsAndNutritions(List<Meal> meals, List<Nutrition> nutritions) {
        mealJournalPanel.setInitialMealsAndNutritions(meals, nutritions);
    }

    public void clearFields() {
        // Resetting the date field to the current date
        model.setValue(new Date());
        // Resetting the meal type combo box to the first item
        mealTypeComboBox.setSelectedIndex(0);
        // Resetting the food item combo box to the first item
        foodItemComboBox.setSelectedIndex(0);
        // Resetting the goal panel by removing all goal fields
        goalPanel.removeAll();
        increaseDecreaseComboBoxes.clear();
        nutrientComboBoxes.clear();
        intensityComboBoxes.clear();
        intensityValueFields.clear();
        intensityUnitComboBoxes.clear();
        // Adding a default goal field
        addGoalField(0);
        applyNutrientComboBoxesListener();
        // Revalidating and repainting the goal panel to reflect changes
        goalPanel.revalidate();
        goalPanel.repaint();
        // Clearing the meal journal panel fields
        mealJournalPanel.clearFields();
    }
}