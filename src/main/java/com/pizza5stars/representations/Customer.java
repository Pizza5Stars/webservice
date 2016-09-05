package com.pizza5stars.representations;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.nio.file.attribute.UserPrincipal;

public class Customer implements UserPrincipal {
    private final int id;
    @NotBlank
    @Length(min = 2, max = 255)
    private final String firstName;
    @NotBlank
    @Length(min = 2, max = 255)
    private final String lastName;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    private String password;

    public Customer() {
        this.id = 0;
        this.email = null;
        this.firstName = null;
        this.lastName = null;
        this.password = null;
    }

    public Customer(int id, String email, String firstName, String lastName, String password) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return firstName + " " + lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
