package com.github.ddehghani.model;

public interface Database {
    public boolean registerUser(String firstName, String lastName, String email, String password);
    public boolean authenticateUser(String email, String password);
}