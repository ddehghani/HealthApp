package com.github.ddehghani.model;

import java.sql.*;

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

    public boolean registerUser(String firstName, String lastName, String sex, String unit, String height, String weight, String dob, String email, String password) {
        if (emailExists(email)) return false;

        String sql = "INSERT INTO users (first_name, last_name, sex, unit, height, weight, dob, email, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, sex);
            stmt.setString(4, unit);
            stmt.setString(5, height);
            stmt.setString(6, weight);
            stmt.setString(7, dob);
            stmt.setString(8, email);
            stmt.setString(9, password);
            int rows = stmt.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String email, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
}