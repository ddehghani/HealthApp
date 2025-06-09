package com.github.ddehghani.model;

import java.sql.*;
import java.text.ParseException;
import java.util.Optional;
import java.util.Date;

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

    public boolean addMeal(Meal meal, String email) {
        return false; // Not implemented in this example
    }
}