package com.pizza5stars.representations;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class Address {
    @NotBlank
    @Length(min = 2, max = 100)
    private final String street;
    @NotBlank
    @Length(min = 2, max = 10)
    private final String zipcode;
    @NotBlank
    @Length(min = 2, max = 10)
    private final String housenumber;
    @NotBlank
    @Length(min = 2, max = 45)
    private final String city;
    @NotBlank
    @Length(min = 2, max = 45)
    private final String firstname;
    @NotBlank
    @Length(min = 2, max = 45)
    private final String lastname;
    @NotBlank
    @Length(min = 3, max = 15)
    private final String phone;

    private final int id;

    public Address() {
        this.street = null;
        this.zipcode = null;
        this.housenumber = null;
        this.city = null;
        this.firstname = null;
        this.lastname = null;
        this.id = 0;
        this.phone = null;
    }

    public Address(int id, String street, String zipcode, String housenumber, String city, String firstname, String lastname, String phone) {
        this.id = id;
        this.street = street;
        this.zipcode = zipcode;
        this.housenumber = housenumber;
        this.city = city;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public String getCity() {
        return city;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }
}
