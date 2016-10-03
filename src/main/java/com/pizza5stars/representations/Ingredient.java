package com.pizza5stars.representations;

import org.hibernate.validator.constraints.NotBlank;

public class Ingredient {
    @NotBlank
    private final String name;
    private final double price;
    private final String category;

    public Ingredient(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
