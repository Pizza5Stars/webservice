package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Pizza;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class PizzaMapper implements ResultSetMapper<Pizza> {
    public Pizza map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {

        String ingredient = r.getString("ingredients");
        ArrayList<String> ingredients = new ArrayList<String>(Arrays.asList(ingredient.split("\\s*,\\s*")));

        return new Pizza(
                r.getInt("id"),
                r.getString("name"),
                r.getString("size_name"),
                ingredients);
    }
}
