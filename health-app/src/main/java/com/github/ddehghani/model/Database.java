package com.github.ddehghani.model;

public interface Database {
    public boolean registerUser(String firstName, String lastName, String sex, String unit, String height, String weight, String dob, String email, String password);
    public boolean authenticateUser(String email, String password);
}