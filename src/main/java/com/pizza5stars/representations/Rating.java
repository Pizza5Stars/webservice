package com.pizza5stars.representations;

import org.hibernate.validator.constraints.NotBlank;

public class Rating {
    @NotBlank
    private final int pizzaId;
    private final double rating;

    public Rating(int pizzaId, double value) {
        this.pizzaId = pizzaId;
        this.rating = value;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public double getRating() {
        return rating;
    }
}
