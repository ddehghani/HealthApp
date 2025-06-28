package com.github.ddehghani.view;

import com.github.ddehghani.model.Meal;
import com.github.ddehghani.model.MealObserver;
import com.github.ddehghani.model.Nutrition;
import com.github.ddehghani.model.ConcreteModel;
import com.github.ddehghani.model.MealSubject;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MealJournalPanel extends JPanel implements MealObserver {
    private List<Meal> meals;
    private List<Nutrition> nutritions;
    private JPanel mealsPanel;
    private JButton leftButton;
    private JButton rightButton;
    private int currentIndex = 0;

    public MealJournalPanel() {
        // register yourself to the subject
        MealSubject subject = ConcreteModel.getInstance();
        subject.addObserver(this);

        meals = new ArrayList<>();
        nutritions = new ArrayList<>();
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 77));
        setOpaque(false);

        leftButton = new MealPanelButton("<");
        rightButton = new MealPanelButton(">");

        mealsPanel = new JPanel();
        mealsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 6));
        mealsPanel.setOpaque(false);

        leftButton.addActionListener(e -> {
            if (currentIndex + 3 < meals.size()) {
                currentIndex += 3;
                updateMealPanel();
            }
        });

        rightButton.addActionListener(e -> {
            if (currentIndex - 3 >= 0) {
                currentIndex -= 3;
                updateMealPanel();
            }
        });

        add(leftButton, BorderLayout.WEST);
        add(rightButton, BorderLayout.EAST);
        add(mealsPanel, BorderLayout.CENTER);
    }

    private void sortMeals() {
        List<Pair> paired = new ArrayList<>();
        for (int i = 0; i < this.meals.size(); i++)
            paired.add(new Pair(this.meals.get(i), this.nutritions.get(i)));
        
        paired.sort((a, b) -> b.meal.getDate().compareTo(a.meal.getDate()));

        this.meals = new ArrayList<>();
        this.nutritions = new ArrayList<>();
        for (Pair p : paired) {
            this.meals.add(p.meal);
            this.nutritions.add(p.nutrition);
        }
    }

    private void updateMealPanel() {
        mealsPanel.removeAll();
        for (int i = Math.min(2 + currentIndex, meals.size() - 1); i >= currentIndex; i--) {
            MealPanel mealBox = new MealPanel(meals.get(i), nutritions.get(i));
            mealsPanel.add(mealBox);
        }
        mealsPanel.revalidate();
        mealsPanel.repaint();
    }

    @Override
    public void update(Meal m, Nutrition n) {
        this.meals.add(m);
        this.nutritions.add(n);
        sortMeals();
        currentIndex = 0;
        updateMealPanel();
    }
    
    public void setInitialMealsAndNutritions(List<Meal> meals, List<Nutrition> nutritions) {
        this.meals = meals;
        this.nutritions = nutritions;
        sortMeals();
        currentIndex = 0;
        updateMealPanel();
    }

    public void clearFields() {
        nutritions.clear();
        meals.clear();
    }
    private static class MealPanel extends JPanel {
        public MealPanel(Meal meal, Nutrition nutrition) {
            setPreferredSize(new Dimension(110, 65));
            setBackground(new Color(255, 255, 255));

            JLabel mealLabel = new JLabel(meal.getType());
            mealLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM, yy");
            String formattedDate = formatter.format(meal.getDate());
            JLabel dateLabel = new JLabel(formattedDate);
            dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel calLabel = new JLabel(String.format("Calories: %.1f", nutrition.getNutrient("ENERGY (KILOCALORIES)")));
            calLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            add(Box.createVerticalGlue());
            add(mealLabel);
            add(Box.createVerticalStrut(3));
            add(dateLabel);
            add(Box.createVerticalStrut(3));
            add(calLabel);
            add(Box.createVerticalGlue());

            StringBuilder sb = new StringBuilder("<html><b>" + meal.getType() + "</b><br><br>");
            sb.append(String.format("Protein: %.1f<br>", nutrition.getNutrient("PROTEIN"))) ;
            sb.append(String.format("Carbs: %.1f<br>", nutrition.getNutrient("CARBOHYDRATE, TOTAL (BY DIFFERENCE)")));
            sb.append(String.format("Fat: %.1f<br>", nutrition.getNutrient("FAT (TOTAL LIPIDS)")));
            sb.append(String.format("Fiber: %.1f<br>", nutrition.getNutrient("FIBRE, TOTAL DIETARY")));
            sb.append("</html>");
            setToolTipText(sb.toString());        
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(120, 90, 195));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(2,2, getWidth() - 5, getHeight() - 5, 20, 20);
            g2.dispose();
        }
    }

    private static record Pair (Meal meal, Nutrition nutrition) {}

    private static class MealPanelButton extends JButton {
        public MealPanelButton(String text) {
            super(text);
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setForeground(new Color(120, 90, 195));
            setFont(getFont().deriveFont(Font.PLAIN, 30f));
        }
    }
}