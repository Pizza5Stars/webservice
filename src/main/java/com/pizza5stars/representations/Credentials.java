package com.pizza5stars.representations;

import org.hibernate.validator.constraints.NotBlank;

public class Credentials {
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;


    public Credentials() {
        this.email = null;
        this.password = null;
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
