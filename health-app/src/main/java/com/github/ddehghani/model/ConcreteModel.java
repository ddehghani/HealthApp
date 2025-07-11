package com.github.ddehghani.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Calendar;
import io.github.cdimascio.dotenv.Dotenv;


/**
 * Main model class.
 * Uses singleton patten.
 */
public class ConcreteModel implements Model, MealSubject {
    // (may want to move these to a config file)
    public static final String PATH_TO_CNF = "/Users/ddehghani/Desktop/Canada Nutrient File-20250623";

    // for swap functionality
    public static final double errorPercentage = 10; // how far off is acceptable
    public static final String[] nutrientsToKeepSimilar = { 
            "ENERGY (KILOCALORIES)", 
            "PROTEIN", 
            "CARBOHYDRATE, TOTAL (BY DIFFERENCE)", 
            "FAT (TOTAL LIPIDS)"
        };

    // follows singleton pattern
    private static ConcreteModel instance;
    private Connection conn;
    private ArrayList<MealObserver> mealObservers = new ArrayList<>();

    public static ConcreteModel getInstance() {
        if (instance == null)
            instance = new ConcreteModel();
        return instance;
    }

    private ConcreteModel() {
        connectToDatabase(); // connect to the my sql database
        initializeTables(); // adds the necessary tables to the database
        // CSVLoader.loadDataFromCSV(conn, PATH_TO_CNF); // load data from csv files takes about a minute or two
    }

    private void connectToDatabase() {
        try {
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String password = dotenv.get("DB_PASSWORD");

            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void initializeTables() {
        try (Statement stmt = conn.createStatement()) {
            // our tables
            stmt.execute(TableDefinitions.USER_PROFILE_TABLE);
            stmt.execute(TableDefinitions.MEAL_TABLE);
            stmt.execute(TableDefinitions.FOOD_ITEM_TABLE);
            // csv tables
            stmt.execute(TableDefinitions.FOOD_SOURCE_TABLE);
            stmt.execute(TableDefinitions.FOOD_GROUP_TABLE);
            stmt.execute(TableDefinitions.FOOD_NAME_TABLE);
            stmt.execute(TableDefinitions.NUTRIENT_NAME_TABLE);
            stmt.execute(TableDefinitions.NUTRIENT_AMOUNT_TABLE);
            stmt.execute(TableDefinitions.MEASURE_NAME_TABLE);
            stmt.execute(TableDefinitions.CONVERSION_FACTOR_TABLE);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    
    @Override
    public void setProfile(UserProfile profile) {
        String sql = "INSERT INTO user_profiles (email, name, sex, unit, height, weight, dob) VALUES (?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, profile.getEmail());
            stmt.setString(2, profile.getName());
            stmt.setString(3, profile.getSex());
            stmt.setString(4, profile.getUnitOfMeasurement());
            stmt.setDouble(5, profile.getHeight());
            stmt.setDouble(6, profile.getWeight());
            stmt.setDate(7, new java.sql.Date(profile.getDob().getTime())); // getTime()'s returns type is long
            stmt.executeUpdate(); // return the number of rows affected (int type)
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public UserProfile getProfile(String email) {
        // take the email, fomulate a query with it, execute it on the database, get the results, make a UserProfile using the results
        String query = "SELECT * FROM user_profiles WHERE email = ?;";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) { // if there is any new rows in result
                return new UserProfile(
                    rs.getString("name"), 
                    rs.getString("sex"),
                    rs.getString("email"),
                    rs.getString("unit"),
                    rs.getDate("dob"),
                    rs.getDouble("height"),
                    rs.getDouble("weight"));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateProfile(UserProfile profile) {
        String sql = "UPDATE user_profiles SET name = ?, sex = ?, unit = ?, height = ?, weight = ?, dob = ? WHERE email = ?;";
        // we set every attribute even tho some main remain the same
        // email is primary key
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, profile.getName());
            stmt.setString(2, profile.getSex());
            stmt.setString(3, profile.getUnitOfMeasurement());
            stmt.setDouble(4, profile.getHeight());
            stmt.setDouble(5, profile.getWeight());
            stmt.setDate(6, new java.sql.Date(profile.getDob().getTime()));
            stmt.setString(7, profile.getEmail());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteProfile(String email) {
        String sql = "DELETE FROM user_profiles WHERE email = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void addMeal(Meal meal, String email) {
        int generatedMealId = 0; // to save auto gen id
        // add a meal into the table and save the auto gen id
        String sql = "INSERT INTO meals (date, type, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(meal.getDate().getTime()));
            stmt.setString(2, meal.getType());
            stmt.setString(3, email);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) 
                generatedMealId = rs.getInt(1); 
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // use the auto gen id to add entries to food items table
        String foodItemsql = "INSERT INTO food_items (meal_id, food_name, quantity, unit) VALUES (?, ?, ?, ?)";
        for (FoodItem item : meal.getFoodItems()) {
            try (PreparedStatement stmt = conn.prepareStatement(foodItemsql)) {
                stmt.setInt(1, generatedMealId);
                stmt.setString(2, item.getName());
                stmt.setDouble(3, item.getQuantity());
                stmt.setString(4, item.getUnit());
                stmt.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        notifyObservers(meal, getMealNutrtionalValue(meal));
    }

    @Override
    public List<Meal> getMeals(String email) {
        Calendar cal = Calendar.getInstance();
        cal.set(1000, Calendar.JANUARY, 1); // MySQL min
        Date minDate = cal.getTime();

        cal.set(9999, Calendar.DECEMBER, 31); // MySQL max
        Date maxDate = cal.getTime();
        
        return getMealsByTimeFrame(email, minDate, maxDate);
    }

    // can be empty set
    public List<String> getAvailableUnits(String foodName) {
        List<String> result = new ArrayList<>();
        String query = 
        """
            SELECT measure_description 
            FROM measure_names m 
            JOIN conversion_factors c ON m.measure_id = c.measure_id
            JOIN food_names f ON f.food_id = c.food_id
            WHERE f.food_description = ?;
        """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Nutrition getMealNutrtionalValue(Meal originalMeal) {
        Nutrition totalNutrition = new Nutrition();
        for (FoodItem item : originalMeal.getFoodItems()) {
            Nutrition nutrition = getFoodItemNutrtionalValue(item);
            totalNutrition = totalNutrition.add(nutrition);
        }
        return totalNutrition;
    }

    public Nutrition getFoodItemNutrtionalValue(FoodItem foodItem) {
        Map<String, Double> nutritions = new HashMap<>();
        int food_id = -1;
        String query = 
        """
            SELECT food_id
            FROM food_names
            WHERE food_description = ?;
        """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodItem.getName());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                food_id = rs.getInt(1);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        query = 
        """
        SELECT nn.nutrient_name, na.nutrient_value
        FROM nutrient_amounts na
        JOIN nutrient_names nn ON na.nutrient_id = nn.nutrient_id
        WHERE na.food_id = ?;
        """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, food_id);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                nutritions.put(rs.getString(1), rs.getDouble(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Nutrition result = new Nutrition(nutritions);
        result.multiplyBy(foodItem.getQuantity());
        if (foodItem.getUnit() == null) // no unit available
            return result;
        // multiply by conversion factor of unit 
        query = 
        """
        SELECT c.conversion_factor_value
        FROM conversion_factors c
        JOIN measure_names m ON m.measure_id = c.measure_id
        WHERE m.measure_description = ?;
        """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodItem.getUnit());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                result.multiplyBy(rs.getDouble(1));
                return result;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return new Nutrition(nutritions);
    }

    public List<String> getFoodNamesWithSameFoodCategoryAs(String foodName) {
        List<String> foodNames = new ArrayList<>();
        String query =
        """
        SELECT food_description 
        FROM food_names 
        WHERE food_group_id = 
            (SELECT food_group_id 
            FROM food_names 
            WHERE food_description = ?);
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                foodNames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return foodNames;
    }

    public String getFoodCategory(String foodName) {
        String query =
        """
        SELECT food_group_name 
        FROM food_groups fg 
        JOIN food_names fn 
        ON fg.food_group_id = fn.food_group_id 
        WHERE fn.food_description = ?;
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, foodName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getFoodNames() {
        List<String> foodNames = new ArrayList<>();
        String sql = "SELECT food_description FROM food_names;";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while(rs.next()) {
                foodNames.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return foodNames;
    }


    @Override
    public List<Meal> getMealsByDate(String email, Date date) {
        return getMealsByTimeFrame(email, date, date);
    }

    @Override
    public List<Meal> getMealsByTimeFrame(String email, Date begin, Date end) {
        // join meals and food items tables to produce meal objects
        String query = 
        """
            SELECT * 
            FROM meals m 
            INNER JOIN food_items f 
            ON f.meal_id = m.id 
            WHERE m.email = ?
            AND m.date >= ? AND m.date <= ?
        """;
        List<Meal> result = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setDate(2, new java.sql.Date(begin.getTime()));
            stmt.setDate(3, new java.sql.Date(end.getTime()));
            ResultSet rs = stmt.executeQuery();
            Meal currMeal = null;
            int currMealId = -1;
            while(rs.next()) {
                String type = rs.getString("type");
                int meal_id = rs.getInt("meal_id");
                String foodName = rs.getString("food_name");
                double quantity = rs.getDouble("quantity");
                String unit = rs.getString("unit");
                Date date = rs.getDate("date");
                if (currMealId != meal_id) { // new meal has to be created
                    currMeal = new Meal(date, new ArrayList<>(), type);
                    result.add(currMeal);
                    currMealId = meal_id;
                }
                currMeal.getFoodItems().add(new FoodItem(foodName, quantity, unit));
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Map<String, Double> getDailyRecommendationsFromCFG() {
        // TO DO
        return null;
    }

    @Override
    public String getNutrientUnit(String nutrient) {
        String query = 
        """
        SELECT nutrient_unit 
        FROM nutrient_names
        WHERE nutrient_name = ?;
        """;
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nutrient);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            } else {
                throw new IllegalArgumentException("Nutrient not found: " + nutrient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getNutrientNames() {
        String query = 
        """        
        SELECT nutrient_name 
        FROM nutrient_names;
        """;
        List<String> nutrientNames = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                nutrientNames.add(rs.getString("nutrient_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nutrientNames;
    }

    @Override
    public void addObserver(MealObserver o) {
        mealObservers.add(o);
    }

    @Override
    public void removeObserver(MealObserver o) {
        mealObservers.remove(o);
    }

    @Override
    public void notifyObservers(Meal m, Nutrition n) {
        for (MealObserver o: mealObservers) {
            o.update(m, n);
        }
    }

    @Override
    public List<FoodItem> getSwappedFoodOptions(Meal selectedMeal, int selectedFoodItemIndex, List<Goal> goals) {
        // calculate nutritions for the selected meal and the selected meal without selected food item
        Nutrition selectedMealNutritionMinusItem = new Nutrition();
        for (int i = 0; i < selectedMeal.getFoodItems().size(); i++) {
            if (i == selectedFoodItemIndex) // skip the food item we want to swap
                continue;

            FoodItem ingredient = selectedMeal.getFoodItems().get(i);
            selectedMealNutritionMinusItem = selectedMealNutritionMinusItem.add(getFoodItemNutrtionalValue(ingredient));
        }

        FoodItem selectedFoodItem = selectedMeal.getFoodItems().get(selectedFoodItemIndex);
        Nutrition selectedFoodItemNutrition = getFoodItemNutrtionalValue(selectedFoodItem);
        Nutrition selectedMealNutrition = selectedMealNutritionMinusItem.add(selectedFoodItemNutrition);

        List<FoodItem> result = new ArrayList<>();
        
        List<String> foodItemAlternatives = getFoodNamesWithSameFoodCategoryAs(selectedFoodItem.getName());
        for (String food: foodItemAlternatives) {
            boolean isValidAlternative = true;
            double high = 1000, low = 0; // initial range of valid quantities

            List<String> units = getAvailableUnits(food); // get available units for the food item
            //calculate nutrition of food item with first unit and quantity 1
            FoodItem altFoodItem = new FoodItem(food, 1, units.isEmpty()? null: units.getFirst());
            Nutrition altFoodItemNutrition = getFoodItemNutrtionalValue(altFoodItem);
            for (Goal g: goals) {
                
                double intensity = g.getIntensity();
                double selectedMealMinusItemNutrient = selectedMealNutritionMinusItem.getNutrient(g.getNutrient());
                double altFoodItemNutrient = altFoodItemNutrition.getNutrient(g.getNutrient());
                double criticalValue = (intensity - selectedMealMinusItemNutrient) / altFoodItemNutrient;
                criticalValue = Math.max(criticalValue, 0);

                double min = g.isIncrease() ? criticalValue : 0;
                double max = g.isIncrease() ? Double.MAX_VALUE : criticalValue;

                low = Math.max(low, min);
                high = Math.min(high, max);


                if (high <= low) { // early exit
                    isValidAlternative = false;
                    break; 
                }
            }
            if (!isValidAlternative) // early exit 
                continue;

          
            for (String nutrient: nutrientsToKeepSimilar) {
                boolean nutrientIsUsedAsGoal = false;
                for (Goal g : goals) {
                    if (g.getNutrient().equals(nutrient)) {
                        nutrientIsUsedAsGoal = true;
                        break;
                    }
                }

                if (nutrientIsUsedAsGoal) {
                    continue;
                }

                double nutrientInOriginalMeal = selectedMealNutrition.getNutrient(nutrient);
                double nutrientInMealWithoutItem = selectedMealNutritionMinusItem.getNutrient(nutrient);
                double nutrientInAltFoodItem = altFoodItemNutrition.getNutrient(nutrient);
                double max = (nutrientInOriginalMeal * (1 + errorPercentage / 100) - nutrientInMealWithoutItem)/nutrientInAltFoodItem;
                double min = (nutrientInOriginalMeal * (1 - errorPercentage / 100) - nutrientInMealWithoutItem)/nutrientInAltFoodItem;

                low = Math.max(low, min);
                high = Math.min(high, max);

                if (high <= low) { // early exit
                    isValidAlternative = false;
                    break; 
                }
            }

            if (!isValidAlternative) // early exit
                continue;
            
            FoodItem updatedFoodItem = new FoodItem(altFoodItem.getName(), (low + high) / 2, altFoodItem.getUnit());
            result.add(updatedFoodItem);
            
            if (result.size() > 5) 
                break;
        }

        for (FoodItem fi : result) {
            System.out.println("-------------------------------------");
            System.out.println(fi);
           
            List<FoodItem> mealFoodItemsLocal = new ArrayList<>(selectedMeal.getFoodItems());
            mealFoodItemsLocal.set(selectedFoodItemIndex, fi);
            Meal swappedMeal = new Meal(selectedMeal.getDate(), mealFoodItemsLocal, selectedMeal.getType());

            // find critical nutrients
            List<String> criticalNutrients =  new ArrayList<>(List.of(nutrientsToKeepSimilar));
            for (Goal g : goals) {
                if (!criticalNutrients.contains(g.getNutrient())) {
                    criticalNutrients.add(g.getNutrient());
                }
            }

            // compare
            Nutrition swappedMealNutrition = getMealNutrtionalValue(swappedMeal);
            for (String nutrient: criticalNutrients) {
                double originalValue = selectedMealNutrition.getNutrient(nutrient);
                double swappedValue = swappedMealNutrition.getNutrient(nutrient);
                double fiValue = getFoodItemNutrtionalValue(fi).getNutrient(nutrient);
                System.out.println(nutrient + ": Original Meal: " + originalValue 
                + "   swapped value: " + swappedValue 
                + " (" + (swappedValue - originalValue) * 100 / originalValue + "%)"
                + " food item: " + fiValue);
            }
        }
        
        return result;
    }
}