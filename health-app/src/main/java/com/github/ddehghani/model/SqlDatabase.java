package com.github.ddehghani.model;

import java.sql.*;
import java.text.ParseException;
import java.util.Optional;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class SqlDatabase implements Database {
    private static SqlDatabase instance;
    private Connection connection;

    private final String DB_PATH = "sql.db"; // Local SQLite DB file

    private SqlDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);
            initializeDatabase(); // create table if not exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SqlDatabase getInstance() {
        if (instance == null) {
            instance = new SqlDatabase();
        }
        return instance;
    }

    private void initializeDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                sex TEXT NOT NULL,
                unit TEXT NOT NULL,
                height TEXT NOT NULL,
                weight TEXT NOT NULL,
                dob TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            );
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String mealTableSql = """
            CREATE TABLE IF NOT EXISTS meals (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL,
                date TEXT NOT NULL,
                type TEXT NOT NULL,
                calories REAL NOT NULL,
                proteins REAL NOT NULL,
                carbs REAL NOT NULL,
                fats REAL NOT NULL,
                FOREIGN KEY (email) REFERENCES users(email)
            );
            """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(mealTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(User user) {
        if (emailExists(user.getEmail())) return false;

        String sql = "INSERT INTO users (first_name, last_name, sex, unit, height, weight, dob, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getSex());
            stmt.setString(4, user.getUnit());
            stmt.setString(5, user.getHeight());
            stmt.setString(6, user.getWeight());
            stmt.setString(7, DATE_FORMAT.format(user.getDob()));
            stmt.setString(8, user.getEmail());
            stmt.setString(9, user.getPassword());
            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<User> authenticateUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (password.equals(storedPassword)) {
                    try {
                        Date dob = DATE_FORMAT.parse(rs.getString("dob"));
                        User user = new User(
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("sex"),
                            rs.getString("unit"),
                            rs.getString("height"),
                            rs.getString("weight"),
                            dob,
                            rs.getString("email"),
                            storedPassword
                        );
                        return Optional.of(user);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return Optional.empty();
                    }
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, sex = ?, unit = ?, height = ?, weight = ?, dob = ?, password = ? WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getSex());
            stmt.setString(4, user.getUnit());
            stmt.setString(5, user.getHeight());
            stmt.setString(6, user.getWeight());
            stmt.setString(7, DATE_FORMAT.format(user.getDob()));
            stmt.setString(8, user.getPassword());
            stmt.setString(9, user.getEmail());
            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String[] getIngredients() {
        return new String[] {
            "Chicken", "Beef", "Fish", "Rice", "Pasta", "Vegetables", "Fruits", "Dairy", "Nuts", "Grains", "Eggs"
        }; // Example ingredients, replace with actual implementation
    }

    public boolean addMeal(Meal meal, String email) {
        String sql = "INSERT INTO meals (email, date, type, calories, proteins, carbs, fats) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, DATE_FORMAT.format(meal.getDate()));
            stmt.setString(3, meal.getType());
            stmt.setDouble(4, meal.getCalories());
            stmt.setDouble(5, meal.getProteins());
            stmt.setDouble(6, meal.getCarbs());
            stmt.setDouble(7, meal.getFats());
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Meal> getMeals(String email) {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT * FROM meals WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date date = DATE_FORMAT.parse(rs.getString("date"));
                String type = rs.getString("type");
                double calories = rs.getDouble("calories");
                double proteins = rs.getDouble("proteins");
                double carbs = rs.getDouble("carbs");
                double fats = rs.getDouble("fats");
                Meal meal = new Meal(date, type, new ArrayList<>(), calories, proteins, carbs, fats);
                meals.add(meal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meals;
    }

    @Override
    public List<Meal> getMeals(String email, String startDate, String endDate) {
        List<Meal> meals = new ArrayList<>();
        String sql = "SELECT * FROM meals WHERE email = ? AND date BETWEEN ? AND ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date date = DATE_FORMAT.parse(rs.getString("date"));
                String type = rs.getString("type");
                double calories = rs.getDouble("calories");
                double proteins = rs.getDouble("proteins");
                double carbs = rs.getDouble("carbs");
                double fats = rs.getDouble("fats");
                Meal meal = new Meal(date, type, new ArrayList<>(), calories, proteins, carbs, fats);
                meals.add(meal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meals;
    }
}
