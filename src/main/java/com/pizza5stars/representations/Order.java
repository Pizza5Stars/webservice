package com.pizza5stars.representations;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Order {

    @NotNull
    @NotEmpty
    private final List<Integer> pizzaIds;
    @Min(1)
    private final int addressId;

    public Order() {
        this.pizzaIds = null;
        this.addressId = 0;
    }

    public Order(List<Integer> pizzaIds, int addressId) {
        this.pizzaIds = pizzaIds;
        this.addressId = addressId;
    }

    public List<Integer> getPizzaIds() {
        return pizzaIds;
    }

    public int getAddressId() {
        return addressId;
    }
}


