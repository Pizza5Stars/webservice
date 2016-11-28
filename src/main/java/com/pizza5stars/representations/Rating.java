package com.pizza5stars.representations;

import org.hibernate.validator.constraints.NotBlank;

public class Rating {
    @NotBlank
    private final int pizza_id;
    private final double rating;

    public Rating(int pizzaId, double value) {
        this.pizza_id = pizzaId;
        this.rating = value;
    }

    public int getPizzaId() {
        return pizza_id;
    }

    public double getRating() {
        return rating;
    }
}
