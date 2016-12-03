package com.pizza5stars.representations;

public class Size {
    private final String name;
    private final int size;
    private final double priceFactor;

    public Size() {
        this.name = null;
        this.size = 0;
        this.priceFactor = 0.0;
    }

    public Size(String name, int size, double priceFactor) {
        this.name = name;
        this.size = size;
        this.priceFactor = priceFactor;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public double getPriceFactor() {
        return priceFactor;
    }
}
