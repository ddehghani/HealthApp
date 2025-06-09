package com.github.ddehghani.model;
import java.util.Optional;

public interface Database {
    public boolean registerUser(User user);
    public Optional<User> authenticateUser(String email, String password);
    public boolean updateUserProfile(User user);
}