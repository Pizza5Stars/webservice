package com.pizza5stars.representations;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Pizza {
    private final int id;
    @NotBlank
    @Length(min = 2, max = 255)
    private final String name;
    @NotBlank
    private final String sizeName;
    @NotEmpty
    @NotNull
    private final List<String> ingredients;

    public Pizza() {
        this.id = 0;
        this.name = null;
        this.sizeName = null;
        this.ingredients = null;
    }

    public Pizza(int id, String name, String sizeName, List<String> ingredients) {
        this.id = id;
        this.name = name;
        this.sizeName = sizeName;
        this.ingredients = ingredients;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSizeName() {
        return sizeName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

}
