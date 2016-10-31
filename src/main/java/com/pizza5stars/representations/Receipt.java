package com.pizza5stars.representations;

import java.util.List;

public class Receipt {
    private final int nr;
    private final double total;
    private final int orderNr;
    private final Address address;
    private final List<String> pizzaNames;

    public Receipt() {
        this.nr = 0;
        this.total = 0;
        this.orderNr = 0;
        this.address = null;
        this.pizzaNames = null;
    }

    public Receipt(int nr, double total, int orderNr, Address address, List<String> pizzaNames) {
        this.nr = nr;
        this.total = total;
        this.orderNr = orderNr;
        this.address = address;
        this.pizzaNames = pizzaNames;
    }

    public int getNr() {
        return nr;
    }

    public double getTotal() {
        return total;
    }

    public int getOrderNr() {
        return orderNr;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getPizzas() {
        return pizzaNames;
    }
}
