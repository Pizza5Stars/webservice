package com.pizza5stars.representations;

import java.util.Map;

public class AuthInfo {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Map tokenMap;

    public AuthInfo(String firstName, String lastName, String email, Map tokenMap) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.tokenMap = tokenMap;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Map getTokenMap() {
        return tokenMap;
    }
}
