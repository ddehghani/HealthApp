package com.github.ddehghani.model;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

public interface Database {
    DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    boolean registerUser(User user);
    Optional<User> authenticateUser(String email, String password);
    boolean updateUserProfile(User user);
    boolean addMeal(Meal meal, String email);
}